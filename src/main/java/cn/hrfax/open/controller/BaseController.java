package cn.hrfax.open.controller;

import cn.hrfax.open.api.ApiResponse;
import cn.hrfax.open.api.ResponseInfo;
import cn.hrfax.open.common.BankApplyBusiCode;
import cn.hrfax.open.common.BankTypeConstant;
import cn.hrfax.open.common.rsa.RSASignature;
import cn.hrfax.open.common.rsa.RSAUtil;
import cn.hrfax.open.common.rsa.RSAkeyConstant;
import cn.hrfax.open.controller.estage.EstageController;
import cn.hrfax.open.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenpw
 * @version 1.0
 * @date 2018/12/7 11:54
 **/
@RestController("/base")
@Api(tags = "基础接口", description = "基础接口")
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstageController.class);

    @Autowired
    private RSAkeyConstant rsAkey;

    //征信进件参数
    private final String encryptAndSignParam="{\n" +
            "\"assurerNo\": \"商户代码\",\n" +
            "\"data\": \"需加密data数据\",\n" +
            "\"sign\": \"签名，调加密签名方法时可为空字符串\",\n" +
            "\"bankType\": \"银行类型，见字典\",\n" +
            "\"busiCode\": \"业务编码，见字典\",\n" +
            "\"platNo\":\"平台编号\"\n" +
            "}";

    @PostMapping("/encryptAndSign")
    @ApiOperation(value = "数据加密及签名接口", notes = "数据加密及签名接口，业务代码busiCode为0000，" +
            "在调用其他进件接口前，请先调用该接口，将所需加密的数据进行加密，同时获取签名，" +
            "将所需加密的data参数及busiCode参数传入即可。" +
            "结果返回后将返回的data和sign值进行替换，整理后的数据可用于调用相关进件接口")
    @ApiImplicitParam(name = "jsonObject", value = encryptAndSignParam,required = true,paramType = "body")
    public Object encryptAndSign(@RequestBody JSONObject jsonObject){
        LOGGER.error("数据加密及签名接口:{}"+jsonObject);
//        String jsonString=JSONObject.toJSONString(jsonObject);
//        JSONObject json=JSON.parseObject(jsonString);
        String busiCode=jsonObject.getString("busiCode");
        if(busiCode==null||!BankApplyBusiCode.ENCRYPT_SIGN.routeBusiCode.equals(busiCode)){
            return new ApiResponse(ResponseInfo.ILLEGAL_PARAM.code, ResponseInfo.ILLEGAL_PARAM.msg+": busiCode错误");
        }
        return encryptSign(jsonObject);
    }

    /**
     * 数据加密及签名
     * @param jsonObject
     * @return
     */
    private Object encryptSign(JSONObject jsonObject){
        String resultStr = jsonObject.toString();
        LOGGER.error("/encryptAndSign 数据加密、签名 resultStr:"+resultStr);
        com.alibaba.fastjson.JSONObject resultJson = com.alibaba.fastjson.JSONObject.parseObject(resultStr);
        String sourceData = resultJson.getJSONObject("data").toString();
        String platNo = resultJson.getString("platNo");
        String dataPubKey = StringUtils.EMPTY;
        String signPrvKey = StringUtils.EMPTY;
        if (platNo.equals(BankTypeConstant.HR_PLAT_NO)) {

            dataPubKey = rsAkey.getHRKJ_DATA_PUBLIC_KEY();
            signPrvKey = rsAkey.getHRKJ_SIGN_PRIVATE_KEY();
        } else {
            dataPubKey = rsAkey.getDATA_PUBLIC_KEY();
            signPrvKey = rsAkey.getSIGN_PRIVATE_KEY();
        }

        String data;
        String sign;
        try{
            //1、数据加密
            data = RSAUtil.encrypt(sourceData, dataPubKey);
            //2、数据进行签名
            sign = RSASignature.sign(sourceData, signPrvKey);
        }catch (Exception e){
            LOGGER.error("/encryptAndSign 数据加密、签名异常",e);
            return new BusiException(ResponseInfo.EXCEPTION.code, ResponseInfo.EXCEPTION.msg);
        }
        JSONObject param = new JSONObject();
        param.put("data",data);
        param.put("sign",sign);
        return new ApiResponse().addData(param);
    }
}

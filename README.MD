
目录介绍：
faceSign：视频调查系统（远程面签）SDK 和java demo文件

digitSign：电子签约系统SDK文件



视频调查系统（远程面签）更新说明：
注意：
线上环境将http://esb.hrfax.cn换成http://signesb.hrfax.cn/
signesb.hrfax.cn单独给面签使用的会更加稳定

目前Android和ios版本sdk使用是测试环境，请注意以下问题：
1、接入前,请提供bankCode给技术人员配置访问权限
2、如果需要上线请联系技术人员切换提供正式环境参数


工行配置活体检测license
活体检测license需要根据包名申请才能使用，
将Android项目的包名、ios项目的bundle identify以及当地分行与合作机构签署的协议电子版一起发到邮箱申请活体检测license，
邮箱地址（lvqingyi@hrfax.cn,yangbiao@hrfax.cn,wuxiaoyang@hrfax.cn,yuyonghui@hrfax.cn）

中行（face++）配置活体检测license
活体检测license需要根据包名申请才能使用，
将Android项目的包名、ios项目的bundle identify一起发到邮箱申请活体检测license，
邮箱地址（lvqingyi@hrfax.cn,yangbiao@hrfax.cn,wuxiaoyang@hrfax.cn,yuyonghui@hrfax.cn）

本次更新：
1、web排队池子，支持自动分配机制，和手动认领机制同时存在
说明：未更新的机构客户经理视频调查还是采用任务自动分配机制（老），已更新sdk的机构客户经理视频调查采用任务手动认领机制（新）。两者可能存在同时并存。
2、app端支持取消排队
说明：发起视频调查的时候，客户可手动取消排队。
3.、弱网环境的优化、排队性能的优化

所有sdk demo都在网盘上面，具体信息如下：
链接: https://pan.baidu.com/s/1Bv_NNxasD5rxs1Hc7j3d_Q 提取码: xirr 

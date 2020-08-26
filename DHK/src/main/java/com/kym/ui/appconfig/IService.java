package com.kym.ui.appconfig;

import com.zzss.jindy.appconfig.Clone;

/**
 * 接口类
 */

public class IService {


    /**
     * SXYQ签约
     */
    public static String SHOUXINYI_SIGN = Clone.HOST + "api.php/receives/shouXinYiSign";//requestId是这边返回过来的,这边请求成功后再请求下一个接口
    /**
     * SXYQ确认签约
     */
    public static String SHOUXINYI_QUEREN_SIGN = Clone.HOST + "api.php/receives/shouXinYiConfirmSign";
    /**
     * SXYQ支付下单接口
     */
    public static String SHOUXINYI_PAY = Clone.HOST + "api.php/receives/shouXinYiPay";
    /**
     * SXYQ支付确认
     */
    public static String SHOUXINYI_QUEREN_PAY = Clone.HOST + "api.php/receives/shouXinYiConfirmSign";

    /**
     * SXYQ签约
     */
    public static String SHOUXINYI_SIGN1 = Clone.HOST + "api.php/shouxinyi/shouXinYiSign";
    /**
     * SXYQ支付下单接口
     */
    public static String SHOUXINYI_PAY1 = Clone.HOST + "api.php/shouxinyi/shouXinYiPay";
    /**
     * SXYQ支付确认
     */
    public static String SHOUXINYI_QUEREN_PAY1 = Clone.HOST + "api.php/shouxinyi/shouXinYiConfirmPay";

    /**
     * 重置还款计划
     * <p>
     * bill_id      计划单号
     */
    public static String CHONGZHIHKPLAN = Clone.HOST + "api.php/repaymentbills/resetPlan";
    /**
     * 删除安全手机号
     */
    public static String DELETEAQMOBILE = Clone.HOST + "api.php/user/cancelSecPhone";
    /**
     * 购买记录
     */
    public static String GOUMAILIST = Clone.HOST + "api.php/user/KadeUpgradeLog";
    /**
     * 获取空卡升级费用
     */
    public static String KADE_UPDATE_MONEY = Clone.HOST + "api.php/user/getKadeUpgradeFee";
    /**
     * 空卡升级支付
     */
    public static String KADEUPGRADEORDER = Clone.HOST + "api.php/order/kadeupgradeorder";


    /**
     * 上传融汇照片
     */
    public static String DC_UPIMG = Clone.HOST + "api.php/user/rhuiUploadPhoto";
    /**
     * 融汇是否上传了照片
     */
    public static String DC_RH_IS_UPIMG = Clone.HOST + "api.php/user/rhuiPhotoStatus";
    /**
     * 融汇获取证件
     */
    public static String RH_ZHENGJIAN_SHOW = Clone.HOST + "api.php/user/rhuiPhotoShow";
    /**
     * 提额明细
     * <p>
     * cardid
     */
    public static String EDUMINGXI = Clone.HOST + "api.php/creditcard/getCardBalanceList";
    /**
     * 修改信用卡额度
     * <p>
     * cardId  卡Id
     * balance    信用卡额度
     */
    public static String UPDATEEDU = Clone.HOST + "api.php/creditcard/changeCreditBalance";
    /**
     * 还款列
     */
    public static String HUANKUANLIST = Clone.HOST + "/api.php/loanRepayBills/getLoanRepaymentList";
    /**
     * 借款接口
     */
    public static String JIEKUAN = Clone.HOST + "api.php/loanRepayBills/LaunchRepayment";
    /**
     * 还款接口
     */
    public static String HUANKUAN = Clone.HOST + "api.php/loanRepayBills/loanRepayment";
    /**
     * 取消借款审核
     */
    public static String STOPJIEKUAN = Clone.HOST + "api.php/loanRepayBills/cancelLoan";
    /**
     * 借款日息
     */
    public static String JIEKUANRIXI = Clone.HOST + "api.php/loanRepayBills/getDayInterest";
    /**
     * 借款应还总额
     */
    public static String JIEKUANTOTALMONEY = Clone.HOST + "api.php/loanRepayBills/getLoanCycle";
    /**
     * 临时提额额度信息
     */
    public static String LINSHITIE = Clone.HOST + "api.php/loanTempQuota/getQuotaInfo";
    /**
     * 申请服务商行业类别
     */
    public static String FUWUSHANGTYPE = Clone.HOST + "api.php/loanAgentApply/getApplyType";
    /**
     * 本金账户余额
     */
    public static String BENJINZHANGHU = Clone.HOST + "api.php/loanPrincipal/getUserPrincipal";
    /**
     * 本金充值
     */
    public static String BENJINCHONGZHI = Clone.HOST + "api.php/order/principalRechargeOrder";
    /**
     * 本金提取
     */
    public static String BENJINTIQU = Clone.HOST + "api.php/trade/principalcash";
    /**
     * 本金转存
     */
    public static String FENRUNZHUANCUN = Clone.HOST + "api.php/loanPrincipal/transfertAccounts";
    /**
     * 本金转存账户余额
     */
    public static String BRNJINYONGHUZHANGHU = Clone.HOST + "api.php/loanPrincipal/userAccount";
    /**
     * 本金记录
     */
    public static String BRNJINJILU = Clone.HOST + "api.php/loanPrincipal/principalList";
    /**
     * 服务商申请
     */
    public static String FUWUSHANGSHENGQING = Clone.HOST + "api.php/loanAgentApply/agentApply";
    /**
     * 是否能使用服务商
     */
    public static String ISUSEFUWUSHANG = Clone.HOST + "api.php/loanAgentApply/userUseAgent";
    /**
     * 用户是否激活额度
     */
    public static String ISJIHUODC = Clone.HOST + "api.php/loanQuota/getQuota";
    /**
     * 获取用户额度
     */
    public static String HQJIHUODC = Clone.HOST + "api.php/loanQuota/openQuota";
    /**
     * 升级权益接口
     */
    public static String SHENGJIQUANYI = Clone.HOST + "api.php/user/getRightsInfo";
    /**
     * 升级权益信息
     */
    public static String SHENGJIQUANYIINFO = Clone.HOST + "api.php/user/getRightsImg";



    /**
     * 安全手机号
     */
    public static String ANQUAN_PHONE = Clone.HOST + "api.php/user/setSafeMobile";

    /**
     * 视频点赞
     * id, 那个视频
     * type,
     */
    public static String VIDEO_DIANZAN = Clone.HOST + "api.php/userAgree/agree";
    /**
     * 视频转发
     * <p>
     * id, 那个视频
     * * type,
     */
    public static String VIDEO_ZHUANFA = Clone.HOST + "api.php/userAgree/share";
    /**
     * 视频预览
     * id, 那个视频
     * * type,
     */
    public static String VIDEO_YULAN = Clone.HOST + "api.php/userAgree/see";
    /**
     * 推广图素材
     * <p>
     * 无参数
     */
    public static String TUIGUANGTU_SUCAI = Clone.HOST + "api.php/shareImg/getUserShareImg";
    /**
     * 推广文案素材
     * <p>
     * 无参数
     */
    public static String TUIGUANGWENAN_SUCAI = Clone.HOST + "api.php/merchantVideo/text";

    /********************************** 以下是门店码的接口 ********************************/
    /**
     * 门店码进件参数
     * 固定参数：resmark
     * 门店码的每个接口都需要
     */
    public static String RESMARK = "DYSREQFUN";
    /**
     * 是否完善门店码信息
     */
    public static String INIT = Clone.HOST + "api.php/codelogic/init";
    /**
     * 门店码行业类型
     */
    public static String GET_HANGYE_TYPE = Clone.HOST + "api.php/function/industrycode";
    /**
     * 提交门店码信息
     * 商户名称：merchantname
     * 商户类型：merchanttype
     * 省：installprovince
     * 城市：installcity
     * 县：installcounty
     * 详细地址：operateaddress
     * 商户联系人姓名：merchantpersonname
     * 商户联系人邮箱：merchantpersonemail
     * 经营类型：merchantcategory
     * 商户联系人手机号：merchantpersonphone
     */
    public static String SUBMIT_INFO = Clone.HOST + "api.php/codelogic/doSelInfo";
    /**
     * 生成二维码
     * 金额：amount
     */
    public static String QRCODE = Clone.HOST + "api.php/codelogic/getUnionCode";
    /**
     * 检测是否支付
     * 生成二维码接口：onumber
     */
    public static String YINLIAN_STATUS = Clone.HOST + "api.php/codelogic/ordstatus";
    /**
     * 门店码账单明细列表
     */
    public static String ZD_LIST = Clone.HOST + "api.php/billPay/ordlist";
    /********************************** 以上是门店码的接口 ********************************/


    /********************************** 以下是简单功能模块接口 ********************************/

    /**
     * 卡测评支付
     * 姓名：name
     * 卡号：bank_no
     * 身份证：card_no
     */
    public static String MEIMEI_KACEPING = Clone.HOST + "cardComment/hihippo_pay";

    /**
     * 轮播图
     * <p>
     * 无参数
     */
    public static String HOME_BANNER = Clone.HOST + "api.php/advert/banner";
    public static String HOME_TEST_BANNER = Clone.HOST + "api.php/advert/banner2";
    /**
     * 上传证件认证图片
     * 图片URL：img_name
     */
    public static String ZHENGJISN = Clone.HOST + "api.php/user/uploadPhoto";
    /**
     * 获取证件
     */
    public static String ZHENGJIAN_SHOW = Clone.HOST + "api.php/user/userPhotoShow";

    /**
     * 排行榜
     * <p>
     * 无参数
     */
    public static String PAIHANGBANG = Clone.HOST + "api.php/user/getTeamRank";

    /**
     * 新优惠升级   购买权益的 购买之后才能升级
     * <p>
     * 无参数
     */
    public static String YOUHUI_SHENGJI = Clone.HOST + "api.php/userUpgrade/showUpgrade";
    /**
     * 新推广升级   购买权益的只能推广才能升级
     * <p>
     * 无参数
     */
    public static String YOUHUI_TUIGUANG = Clone.HOST + "api.php/userUpgrade/showRecommend";

    /**
     * 支付宝的
     * 支付升级
     * 当前等级：level
     * 升级等级：upgradeLevel
     */
    public static String YONGHUI_PAY = Clone.HOST + "api.php/order/specialUpgradeOrder";
    /**
     * 获取签约通道
     * 卡ID：cardid
     */
    public static String CARD_JIEBANG_LIST = Clone.HOST + "api.php/creditcard/untyCardList";
    /**
     * 信用卡解绑
     * 卡ID：cardid
     * id,签约通道的ID
     * type  区分是还款刷卡养卡代偿
     */
    public static String CARD_JIEBANG = Clone.HOST + "api.php/creditcard/untyCard";
    /**
     * 信用卡删除
     * 卡ID：信用卡删除
     */
    public static String CARD_DEL = Clone.HOST + "api.php/creditcard/delCard";
    /********************************** 以上是简单功能模块接口 ********************************/


    /********************************** 以下是快捷收款接口 ********************************/
    /**
     * 快捷收款-通道列表
     */
    public static String KUAIJIE_SHOUKUAN = Clone.HOST + "api.php/receives/getPass";

    /**
     * 刷卡进去的页面
     * <p>
     * 无参数
     */
//    public static String KUAIJIE_CREATE_LIST = Clone.HOST + "api.php/receives/getBankList";
    public static String KUAIJIE_CREATE_LIST = Clone.HOST + "api.php/receives/getBankList";
    /**
     * 快捷收款-银生宝进件
     * 通道ID：id
     * 卡ID：cardid
     * 收款金额：money
     */
    public static String KUAIJIE_PAY = Clone.HOST + "api.php/receives/transactPay";
    /**
     * 快捷收款-新生进件
     * 通道ID：id
     * 卡ID：cardid
     * 收款金额：money
     * 城市码：citycode
     * 商户码：mcc
     */
    public static String DZ_KUAIJIE_PAY = Clone.HOST + "api.php/receives/XStransactPay";
    public static String XS_KUAIJIE_PAY = Clone.HOST + "api.php/receives/XSNtransactPay";

    /**
     * 快捷收款-新生出款
     * 订单ID：merOrderId
     * 验证码：smsCode
     */
    public static String DZ_KUAIJIE_PAY_SUB = Clone.HOST + "api.php/receives/ConfirmPay";
    public static String XS_KUAIJIE_PAY_SUB = Clone.HOST + "api.php/receives/XSNConfirmPay";
    /**
     * 快捷收款-快付通进件
     * 通道ID：id
     * 卡ID：cardid
     * 收款金额：money
     */
    public static String KFT_MSG = Clone.HOST + "api.php/receives/kftTransactPay";
    /**
     * 快捷收款-快付通出款
     * 订单ID：orderid
     * 验证码：smscode
     */
    public static String KFT_PAY = Clone.HOST + "api.php/receives/kftPayConfirm";
    /**
     * 快捷收款-易宝进件
     * 通道ID：id
     * 卡ID：cardid
     * 收款金额：money
     */
    public static String YIBAO = Clone.HOST + "api.php/receives/ybTransactPay";
    /**
     * 快捷收款-易宝图片认证状态
     * 通道ID：id
     */
    public static String YIBAO_IMG = Clone.HOST + "api.php/user/userPhotoStatus";
    /**
     * 快捷收款-融汇
     * 通道ID：id
     * 卡ID：cardid
     * 收款金额：money
     */
    public static String RH_PAY = Clone.HOST + "api.php/receives/rhKuaiPay";
    /**
     * 快捷收款-落地商户城市
     */
    public static String XS_LD_CITY = Clone.HOST + "api.php/public/getCity";
    /**
     * 快捷收款-落地商户类别
     * 城市码：citycode
     */
    public static String XS_LD_HY = Clone.HOST + "api.php/public/getMcc";
    /**
     * 快捷收款-天付宝
     * 通道ID：id
     * 卡ID：cardid
     * 收款金额：money
     * 城市码：citycode
     * 商户码：mcc
     */
    public static String TFB_MSG = Clone.HOST + "api.php/receives/tfbTransactPay";
    /**
     * 快捷收款-天付宝出款
     * 订单ID：orderid
     * 验证码：smscode
     */
    public static String TFB_PAY = Clone.HOST + "api.php/receives/tfbPayConfirm";

    /**
     * 快捷收款-腾付通获取验证码
     */
    public static String TFT_MSG = Clone.HOST + "api.php/receives/tftTransactPay";
    /**
     * 快捷收款-腾付通确认支付
     */
    public static String TFT_PAY = Clone.HOST + "api.php/receives/tftConfirmPay";

    /**
     * 快捷收款-佳付通交易
     * money，cardid，id
     */
    public static String JFT_TRADE = Clone.HOST + "api.php/receives/jftTransactPay";

    /**
     * 快捷收款-加强佳付通交易
     * money，cardid，id
     */
    public static String JQJFT_TRADE = Clone.HOST + "api.php/receives/jftTransactPayJD";

    /**
     * 快捷收款-首信易签约
     */
    public static String SXY_QIANYUE = Clone.HOST + "api.php/receives/shouXinYiPay";

    /**
     * 快捷收款-首信易绑卡
     */
    public static String SXY_BANGKA = Clone.HOST + "api.php/receives/sxyConfirmPay";
    /********************************** 以上是快捷收款接口 ********************************/


    /********************************** 以上是还款接口 ********************************/
    /**
     * 还款-信用卡列表
     * type(D-大额 X-小额)
     */
    public static String NEWXINYONGKA = Clone.HOST + "api.php/repaymentcard/banklist";
    /**
     * 制定计划签约通道
     * cardid
     */
    public static String PLAN_SIGN = Clone.HOST + "api.php/repaymentcard/getPass";
    /**
     * 统一签约申请接口
     * id,cardid,params(数组)
     */
    public static String TONGYI_SIGN_SHENQING = Clone.HOST + "api.php/repayments/commonTreatyApply";
    /**
     * 统一签约确认接口
     * id,orderid,smsSeq,authCode(验证码)
     */
    public static String TONGYI_SIGN_QUEREN = Clone.HOST + "api.php/repayments/commonTreatyConfirm";
    /**
     * 还款-预览计划
     * 'cardid'   卡ID
     * ,'money','   钱
     * type',  还款类型
     * 'number', 还款笔数
     * 'area',   还款城市
     * date',   还款日期
     * 'mode',' 还款模式
     * cardsurplus'   预留金额,卡里剩余金额
     * channelType    根据type增加的
     */
    public static String NEW_HK_PLAN = Clone.HOST + "api.php/repaymentbills/billpreview";
    /**
     * 还款-提交计划
     * <p>
     * auth_code  验证码
     * cardnum         信用卡卡号
     * obile        手机号
     */
    public static String ADD_HK_PLAN = Clone.HOST + "api.php/repaymentbills/billadd";
    /**
     * 还款-计划列表
     * <p>
     * p  页数
     * cardid  卡ID
     */
    public static String HK_LIST = Clone.HOST + "api.php/repaymentbills/getbilllist";
    /**
     * 还款-计划列表详情
     * <p>
     * bill_id   计划单号
     */
    public static String HK_LIST_DETAIL = Clone.HOST + "api.php/repaymentbills/billdetail";

    /**
     * 还款-终止计划
     * <p>
     * bill_id   计划单号
     */
    public static String STOP_PlAN = Clone.HOST + "api.php/repaymentbills/billinvalid";

    /**
     * 腾付通进件
     */
    public static String TENGFUTONG = Clone.HOST + "api.php/repayment/tftEntry";
    /**
     * 融汇绑卡
     */
    public static String RONGHUI_BANGKA = Clone.HOST + "api.php/repayment/rhBindCard";
    /**
     * 融汇绑卡已开通快捷支付
     */
    public static String RONGHUI_KUAIJIE = Clone.HOST + "api.php/repayment/changeBindCardStatus";

    /********************************** 以上是还款接口 ********************************/


    /********************************** 以上是我的消息接口 ********************************/
    /**
     * 系统消息
     */
    public static final String GET_SYSTEM_MESSAGE = Clone.HOST + "api.php/msgPush/sysmessage";

    /**
     * 个人消息
     */
    public static final String GEREN_MESSAGE = Clone.HOST + "api.php/msgPush/permessage";
    /********************************** 以上是我的消息接口 ********************************/


    /********************************** 以下是推广的接口 ********************************/

    /********************************** 以上是推广的接口 ********************************/


    /********************************** 以下是账户的接口 ********************************/


    /********************************** 以上是账户的接口 ********************************/


    /********************************** 以上是我的零钱的接口 ********************************/
    /**
     * 我的零钱余额
     */
    public static String WODE_LINGQIAN = Clone.HOST + "api.php/userchange/account";
    /**
     * 我的零钱充值随机数
     */
    public static String WODE_SUIJISHU = Clone.HOST + "api.php/userchange/userEncrypt";
    /**
     * 我的零钱充值
     * 随机数：encrypt
     * 金额：money
     */
    public static String WODE_LINGQIAN_CHONGZHI = Clone.HOST + "api.php/userchange/userRecharge";
    /**
     * 我的零钱提现
     * 提现金额：amount
     */
    public static String WODE_LINGQIAN_TIXIAN = Clone.HOST + "api.php/trade/userAccount";
    /**
     * 我的零钱明细
     */
    public static String WODE_LINGQIAN_MINGXI = Clone.HOST + "api.php/trade/userAccountlsit";
    /********************************** 以上是我的零钱的接口 ********************************/


    /********************************** 以上是贷偿的接口 ********************************/
    /**
     * 判断用户融汇空卡是否填写地区
     */
    public static String IS_CHUANDIQU = Clone.HOST + "api.php/user/checkRhuiKadeArea";
    /**
     * 新融汇上传城市
     */
    public static String RH_ShangChuan_CITY = Clone.HOST + "api.php/user/rhuiKadeAreaAdd";
    /**
     * 融信分
     * keep  已守约
     * nokeep 未守约
     * waitkeep 待守约  这三个是接口中的值
     */
    public static String RONGCINFEN_INFO = Clone.HOST + "api.php/rongxinApi/getRxScore";
    /**
     * 信用足迹
     * type同上
     */
    public static String RONGXINFEN_DETAIL = Clone.HOST + "api.php/rongxinApi/promiseDetail";
    /**
     * 贷偿信用卡列表
     */
    public static String DC_BANK_LIST = Clone.HOST + "api.php/kadecard/banklist";
    /**
     * 贷偿通道
     * 卡ID：cardid
     */
    public static String DC_BANK_TONGDAO = Clone.HOST + "api.php/kadecard/getPass";
    /**
     * 贷偿计划列表
     * p
     */
    public static String DC_PLAN_LIST = Clone.HOST + "api.php/kadebills/getbilllist";
    /**
     * 空卡代还预览计划
     * 卡ID：cardid
     * 金额：money
     * 模式：modeltype"
     * paysource
     */
    public static String DC_GHT_PREVIEW = Clone.HOST + "api.php/kadebills/billpreview";
    /**
     * 提交空卡代还计划
     * cardid
     * code
     * city
     */
    public static String DC_GHT_SUBMIT = Clone.HOST + "api.php/kadebills/billadd";
    /**
     * 支付空卡代还手续费
     */
    public static String DC_GHT_FEE = Clone.HOST + "api.php/kadebills/billpay"; // 参数：bill_id
    /**
     * 空卡代还单个计划详情
     */
    public static String DC_GET_DETAILS = Clone.HOST + "api.php/kadebills/billdetail"; //bill_id
    /**
     * 空卡代还额度
     */
    public static String DC_GET_EDU = Clone.HOST + "api.php/kadebills/getUserAllowance";
    /**
     * 融信分是否够500分
     */
    public static String DC_SCORE = Clone.HOST + "api.php/verifykade/verifyPayment";
    /**
     * 统一签约\进件接口
     * 通道ID：id
     * 卡ID：cardid
     * json：params = merchantName   installProvince   installCity   installCounty
     */
    public static String ALL_SIGN = Clone.HOST + "api.php/kade/commonTreatyApply";
    /**
     * 统一签约确认接口
     * id(通道id)  orderNo smsCode
     */
    public static String ALL_QUEREN = Clone.HOST + "api.php/kade/commonTreatyConfirm ";
    /**
     * 还款的公用的三级联动    好几个地方都有用到这个
     * <p>
     * 无参数
     */
    public static String RH_SIGN_CITY = Clone.HOST + "api.php/function/getArea";
    /********************************** 以上是空卡代还的接口 ********************************/


    /********************************** 以下是消费计划的接口 ********************************/
    /**
     * 消费计划-信用卡列表
     * <p>
     * 无参数
     */
    public static String XF_BANK_LIST = Clone.HOST + "api.php/aatYangka/getBankList";
    /**
     * 消费计划-信用卡签约通道列表
     * 卡ID：cardid
     */
    public static String XF_BANK_SIGN_LIST = Clone.HOST + "api.php/consumecard/getPass";
    /**
     * 消费计划-快付通申请签约
     * 卡ID：cardid
     */
    public static String XF_SHENQING_SIGN = Clone.HOST + "api.php/consume/kftTreaty";
    /**
     * 消费计划-快付通确认签约
     * 订单号：orderid
     * 短信流水号：smsSeq
     * 校验码：authCode
     */
    public static String XF_QUEREN_SIGN = Clone.HOST + "api.php/consume/kftTreatyConfirm";
    /**
     * 消费计划-佳付通申请签约
     */
    public static String XF_JFT_SHENQING_SIGN = Clone.HOST + "api.php/consume/treatyApplyJFT";
    /**
     * 消费计划-佳付通确认签约
     */
    public static String XF_JFT_QUEREN_SIGN = Clone.HOST + "api.php/consume/treatyConfirmJFT";
    /**
     * 消费计划-佳付通加强版签约
     */
    public static String XF_JQJFT_SHENQING_SIGN = Clone.HOST + "api.php/consume/treatyApplyJFTX";
    /**
     * 消费计划-佳付通加强版确认签约
     */
    public static String XF_JQJFT_QUEREN_SIGN = Clone.HOST + "api.php/consume/treatyConfirmJFTX";
    /**
     * 消费计划-九鼎佳付通加强版签约
     */
    public static String XF_JDJQJFT_SHENQING_SIGN = Clone.HOST + "api.php/consume/treatyApplyJFTJDX";
    /**
     * 消费计划-九鼎佳付通加强版确认签约
     */
    public static String XF_JDJQJFT_QUEREN_SIGN = Clone.HOST + "api.php/consume/treatyConfirmJFTJDX";
    /**
     * 消费计划-首信易签约
     */
    public static String SXY_YANGKA_QIANYUE = Clone.HOST + "api.php/consume/treatyApplySXY";
    /**
     * 消费计划-首信易确认签约
     */
    public static String SXY_YANGKA_QUERENQIANYUE = Clone.HOST + "api.php/consume/treatyConfirmSXY";
    /**
     * 消费计划-预览计划
     * 卡ID：cardid
     * 消费金额：money
     * 消费结算方式：type
     * 消费笔数：number
     * 消费城市：area
     * 消费时间：date
     */
    public static String XF_YULAN_PLAN = Clone.HOST + "api.php/consumebills/billpreview";
    /**
     * 消费计划-添加计划
     * 卡ID：cardid
     * 手机号：mobile
     * 短信验证码：auth_code
     */
    public static String XF_ADD_PLAN = Clone.HOST + "api.php/consumebills/billadd";
    /**
     * 消费计划-计划列表
     * 页数：p
     * cardid  卡ID
     */
    public static String XF_PLAN_LIST = Clone.HOST + "api.php/consumebills/getbilllist";
    /**
     * 消费计划-计划列表详情
     * 计划ID：bill_id
     */
    public static String XF_PLAN_LIST_DETAIL = Clone.HOST + "api.php/consumebills/billdetail";
    /**
     * 消费计划-用户终止计划
     * 计划标识：bill_id
     */
    public static String XF_STOP_PLAN = Clone.HOST + "api.php/consumebills/billinvalid";
    /********************************** 以上是消费计划的接口 ********************************/

    /**
     * 所有信用卡列表
     * <p>
     * 无参数
     */
    public static String ALL_CARD_LIST = Clone.HOST + "api.php/user/getUserCards";
    /**
     * 获取订单接口
     */
    public static String GET_ORDER = Clone.HOST + "api.php/order/new";
    /**
     * 银行卡评测-短信授权
     */
    public static String HIHIPPO_SMS = Clone.HOST + "api.php/cardComment/hihippo_sms";
    /**
     * 银行卡评测-短信授权验证及测评
     */
    public static String HIHIPPO_CODE = Clone.HOST + "api.php/cardComment/hihippo_code";
    public static String SHILI = Clone.HOST + "api.php/cardComment/hihippo_give";
    /**
     *
     */



    /**
     * 退出登录
     */
    public static final String USER_LOGOUT = Clone.HOST + "api.php/user/logout";
    /**
     * 图片文件上传
     */
    public static final String UPLOAD_IMG = Clone.HOST + "api.php/upload/img";
    /**
     * 信用卡新增接口
     * <p>
     * mobile       预留手机号
     * bank_name        银行名称
     * balancee     信用卡额度
     * bills        账单日
     * repayment   还款日
     * pauth_code  短信验证码
     * bank_no   卡号
     * safecode   cvv2安全码
     * validityperiod   有效期
     */
    public static final String CREDITCARD_ADD = Clone.HOST + "api.php/creditcard/add";
    /**
     * 获取支持银行卡列表
     */
    public static final String GET_BANKNAME = Clone.HOST + "api.php/creditcard/getBankName";
    /**
     * 获取账单列表
     */
    public static final String GET_BILLLIST = Clone.HOST + "api.php/bills/getbilllist";
    /**
     * 获取账单列表详情
     */
    public static final String GET_BILLINFO = Clone.HOST + "api.php/bills/getbillInfo";
    /**
     * 账单预览
     */
    public static final String BILLPREVIEW = Clone.HOST + "api.php/bills/billpreview";
    /**
     * 密码重置
     */
    public static final String RESET_PASSWORD = Clone.HOST + "api.php/user/changePwd";
    // 14.获取等级信息   用户升到哪里就怎么显示
    //无参数
    public static final String USER_UPGRADE = Clone.HOST + "api.php/user/upgrade";
    // 15.获取短信接口
    /*
    有的是两个参数,有的是三个参数
    cardnum         信用卡卡号
    mobile        手机号
    type      区分那边发的短信,列子,忘记密码获取的短信,或者是注册账号的短信,再或者卡支付的短信,
     */
    public static final String AUTHCODE = Clone.HOST + "api.php/mer/authCode";





    /*
        26.APP检测更新接口
        参数
        version_code      app的版本号
        os      区分安卓和IOS 安卓是1 苹果是2
     */
    public static final String UPGRADE = Clone.HOST + "api.php/version/upgrade";
    // 30.添加信用卡还款计划
    public static final String ADD_CARD_BILL = Clone.HOST + "api.php/bills/billadd";
    // 31.头像上传接口 headimage 参数名
    public static final String UPLOADAVATAR = Clone.HOST + "api.php/user/uploadavatar";


    // 34.信用卡签约短信验证码
    public static final String GET_SIGN_CODE = Clone.HOST + "api.php/creditcard/GetSignSms";
    // 35.信用卡签约
    public static final String SIGN_CREDIT_CARD = Clone.HOST + "api.php/creditcard/creditcardsign";












    /*      获取token接口
          Account("credit_android_01"); 账户名
          Key("6D22CF9BBBD804E730A7AF355F44CFBD");  密码
          数据目前都是死的
       */
    public static final String GET_TOKEN = Clone.HOST + "api.php/base/accessAuth";
        /*
    23.登录前弹窗

    无参数
     */
    public static final String MSGPUSH_FORCE = Clone.HOST + "api.php/msgPush/force";
    /**
     * 登录接口
     * <p>
     * mobile        //登录的账号手机号
     * auth_code        //换手机时设备验证,需要手机号的验证码
     * password  //密码
     * cid     //个推获取用户的ID
     * accessToken"         公用的token
     * <p>
     * 下边的这些可以为空
     * is_openposition    //是否开启定位
     * latitude     //如果获取定位就要获取经纬度
     * longitude     //如果获取定位就要获取经纬度
     * urrentCity     //当前城市
     * currentProvinces       //当前省份
     * os_type", "android"       //区分安卓青苹果,后面的写死就行
     * device"      手机的设备号
     */

    public static final String LOGIN = Clone.HOST + "api.php/user/login";
    /**
     * 获取用户信息
     * <p>
     * 没有参数
     */
    public static final String USER_INFO = Clone.HOST + "api.php/user/info";
    // 46.用户推广升级
    public static final String UPGRADENEW = Clone.HOST + "api.php/user/upgradeNew";
    /**
     * 交易量
     * <p>
     * 无参数
     */
    public static String JiaoYiLiang = Clone.HOST + "api.php/report/yesVolume";
    /**
     * 小红点提示
     * <p>
     * 无参数
     */
    public static String SMALLRED = Clone.HOST + "api.php/icon/iconShow";
    /**
     * 我的商户
     * <p>
     * 无参数
     */
    public static final String USER_MYMERCHANT = Clone.HOST + "api.php/user/mymerchant";
    /**
     * 我的子商户
     * 页码:p
     * 区分新客服老客服贵宾客服：lfid
     * 商户认证状态：status_auth
     */
    public static final String MY_CHILD = Clone.HOST + "api.php/user/mychild";
    /**
     * 推广内容
     * <p>
     * 无参数
     */
    public static String SHARE_NEW = Clone.HOST + "api.php/advert/shareStrategy";
    /**
     * 智收明细
     * <p>
     * 无参数
     */
    public static String ZD_LIST1 = Clone.HOST + "api.php/aatYangka/receiveList";
    /**
     * 智收明细
     * <p>
     * 无参数
     */
    public static String ZD_LIST11 = Clone.HOST + "api.php/receives/receiveList";
//    api.php/receives/receiveList
    /**
     * 视频接口
     * <p>
     * 无参数
     */
    public static String VIDEO = Clone.HOST + "api.php/merchantVideo/getVideo";












    // 42. 信用卡账单日和还款诶
    /*
    cardid 卡Id
    bills        账单日
     repayment   还款日
     */
    public static final String CHANGE_BANK_INFO = Clone.HOST + "api.php/creditcard/changbankinfo";
    // 43.删除还款计划
    public static final String DELETE_PLAN = Clone.HOST + "api.php/bills/billsdel";
    // 44.还款计划确认支付
    public static final String CONFIRM_BILL_PAY = Clone.HOST + "api.php/bills/billspay";

    // 45.获取APP平台信息
    public static final String GET_MERCHANT = Clone.HOST + "api.php/mer/getMerchant";
    // 46.付费升级
    //无参数
//    public static final String UPGRADEORDER = Clone.HOST + "api.php/order/upgradeorder";
//    public static final String UPGRADEORDER = Clone.HOST + "api.php/order/upgradeorderLatest";
    public static final String UPGRADEORDER = Clone.HOST + "api.php/user/teamupgradepay";

    // 60.继续支付
    public static final String REPAYMENT_AGAIN = Clone.HOST + "api.php/bills/afreshdeductions";


    //********************************分润账户的****************************************************************
    // 36.分润账户
    public static final String GET_ACCOUNT_BALANCE = Clone.HOST + "api.php/trade/getaccountbalance";


    //********************************还款账户分润明细详情***************************************************************
    //还款账户分润所有的列表
    //有两个默认的参数,开始的时间和结束的时间
    public static final String SPLITTER_DETAIL = Clone.HOST + "api.php/trade/splitter_detail";
    //还款单日账户收益列表
    public static final String SPLITTER_TOTAL_TIME = Clone.HOST + "api.php/trade/splitter_total_time";
    /**
     * 还款,收款,代偿单日详情
     * 页数：p  现在默认是1,只展示最新的
     * 下边的三个目前是空的
     * type      区分还款还是收款
     * starttime   开始时间
     * endtime      结束时间
     */
    public static final String SPLITTERLIST = Clone.HOST + "api.php/trade/splitterList";
    // 还款的提现记录  请你去参数  p页码
    public static final String CASH_LSIT = Clone.HOST + "api.php/trade/cashlsit";


    //********************************收款账户收益列表****************************************************************
    //收款账户收益列表   包括刷卡和养卡
    public static final String SPLITTER_SHOUKUAN_DETAIL = Clone.HOST + "api.php/trade/splitterReceive_detail";
    //收款账户单日列表   //starttime  开始时间
    public static final String SPLITTER_SHOUKUAN_TOTAL_TIME = Clone.HOST + "api.php/trade/splitter_receive_total";
    // 收款提现规则
    public static final String SHOUKUAN_TIXIAN = Clone.HOST + "api.php/trade/receiveCashInfo";
    //收款账户提现   amount金额
    public static final String SHOUKUAN_ACCOUNT = Clone.HOST + "api.php/trade/receivecash";
    //收款体现明细    p
    public static final String SHOUKUAN_CASH_BACKLSIT = Clone.HOST + "api.php/trade/receivelsit";


    //********************************贷偿账户收益明细***************************************************************
    // 贷偿账户收益列表
    public static String DAICHANG_MINGXI = Clone.HOST + "api.php/trade/splitterKade_detail";
    //贷偿账户单日收益列表
    public static String DAICHANG_DANGRIFENRUN = Clone.HOST + "api.php/trade/splitter_kade_total";
    //贷偿提现规则
    public static String DAICHANG_TIXIAN_YONFHU = Clone.HOST + "api.php/trade/kadeCashInfo";
    //贷偿提现   amount金额
    public static String DAICHANG_TIXIAN = Clone.HOST + "api.php/trade/kadecash";
    //贷偿提现明细    p页码
    public static String DAICHANG_TIXIAN_JILU = Clone.HOST + "api.php/trade/kadelsit";


    //********************************升级账户收益***************************************************************
    //升级账户收益列表
    public static String SHENGJI_MINGXI = Clone.HOST + "api.php/trade/splitterUpdate_detail";
    // 升级账户收益明细详情
    public static String SHENGJI_DANGRIFENRUN = Clone.HOST + "api.php/trade/splitter_update_total";
    //升级提现规则
    public static final String SHENGJI_TIXIAN = Clone.HOST + "api.php/trade/updateCashInfo";
    //升级提现   amount金额
    public static final String SHENGJI_ACCOUNT = Clone.HOST + "api.php/trade/updatecash";
    // 升级体现明细列表          p页码
    public static final String SHENJILIST = Clone.HOST + "api.php/trade/updatelsit ";


    //********************************活动账户收益****************************************************************
    //活动账户收益列表
    public static String HUODONG_MINGXI = Clone.HOST + "api.php/trade/splitterActivity_detail";
    //活动单日收益列表
    public static String HUODONG_DANGRIFENRUN = Clone.HOST + "api.php/trade/splitter_activity_total";
    // 活动账户当日列表详情
    /* 页数：p  现在默认是1,只展示最新的
     * 下边的三个目前是空的
     * type      区分还款还是收款
     * starttime   开始时间
     * endtime      结束时间*/
    public static String HUODONGFENRUNMINGXI = Clone.HOST + "api.php/trade/activity_list";
    // 活动-提现规则
    public static final String HUODONG_TIXIAN = Clone.HOST + "api.php/trade/activityCashInfo";
    //活动-提现
    public static final String HUODONG_ACCOUNT = Clone.HOST + "api.php/trade/activitycash";
    //活动-提现明细
    public static final String HUODONGLIST = Clone.HOST + "api.php/trade/activity_cash_list ";


    //********************************返现的****************************************************************
    //返现账户收益列表
    public static final String SPLITTER_BACK_DETAIL = Clone.HOST + "api.php/trade/splitterback_detail";
    //返现账户单日列表
    public static final String SPLITTER_BACK_TOTAL_TIME = Clone.HOST + "api.php/trade/splitterback_total_time";
    // 返现列表当日详情
    public static final String SPLITTER_BACKLIST = Clone.HOST + "api.php/trade/splitterbackList";
    // 返现提现费用信息
    public static final String BACKCASH_INFO = Clone.HOST + "api.php/trade/getBackCashInfo";
    // 返现提现规则接口   提交参数amount金额
    public static final String ACCOUNT_CASHBACK = Clone.HOST + "api.php/trade/accountcashback";
    // 返现提现的明细列表     参数p页码
    public static final String CASH_BACKLSIT = Clone.HOST + "api.php/trade/cashbacklsit";


    //********************************提现的****************************************************************
    // 提现获取用户结算卡信息   每个提现第一个的接口都是这个,
    public static final String ACCESSTODEBIT = Clone.HOST + "api.php/user/accesstodebit";
    // 提现规则提示  如手续费,
    public static final String ACCESSINGLEVELINFORMATION = Clone.HOST + "api.php/user/accessinglevelinformation";
    // 检测是否授权支付宝
    public static String ZHIFUBAO_IS_SHOUQUAN = Clone.HOST + "api.php/aliapi/isShouQuan";
    /**
     * 支付宝授权
     * user_id
     * code
     */
    public static String ZHIFUBAO_SHOUQUAN = Clone.HOST + "api.php/aliapi/aliShouQuan";
    //获取支付宝信息
    public static String ZHIFUBAO_USERINFO = Clone.HOST + "api.php/aliapi/aliUserInfo";
    // 38.账户提现接口   提交参数amount金额
    public static final String ACCOUNT_CASH = Clone.HOST + "api.php/trade/accountcash";




    /**
     * 获取实名省份
     */
    public static final String GET_BANKPROVINCE = Clone.HOST + "api.php/ysbank/getBankProvince";
    // 18.获取城卡的市省会信息,支行所办理的城市    province省份
    public static final String GET_BANKCITY = Clone.HOST + "api.php/ysbank/getBankCity";
    /*     19.获取卡的支行信息
             province省份
            city城市
            bank_name储蓄卡的银行名称,什么银行
           bankSub_key模糊查询的关键字*/
    public static final String GET_ZBANK = Clone.HOST + "api.php/ysbank/getZBank";
    /*  储存卡银行列表
        province省份
        city城市*/
    public static final String GET_SETBANKNAME = Clone.HOST + "api.php/user/getSetBankName";


    /**
     * 我的实名认证
     * name;姓名
     * dcard,身份证号
     * bank_no 储蓄卡卡号
     * provincename,储蓄卡的支行省份的名字
     * cityname,储蓄卡支行城市的名字
     * bank_name 储蓄卡的银行名称,什么银行
     * bank_sub  储蓄卡的支行信息
     * bank_mobile 预留手机号
     * bank_code 预留手机号的验证码
     */
    public static String NEW_SHIMING = Clone.HOST + "api.php/htmluser/addSaveCard";

    /**
     * 储蓄卡修改
     * bank_no 储蓄卡卡号
     * provincename,储蓄卡的支行省份的名字
     * cityname,储蓄卡支行城市的名字
     * bank_name 储蓄卡的银行名称,什么银行
     * bank_sub  储蓄卡的支行信息
     * bank_mobile 预留手机号
     * bank_code 预留手机号的验证码
     */
    public static String NEW_CXK_UPDATE = Clone.HOST + "api.php/card/savecard";


    //我的费率

    /**
     * 新手教程又称
     * 类型：type
     * 实名问题：sm,收款问题：sk,还款问题：hk,融信分：rx,账单问题：zd,其他问题：qt
     * mark：ksi8wjk
     */
    public static String Course = Clone.HOST + "api.php/link/helpCenter";
    /**
     * 费率报表
     */
    public static String BAOBIAO = Clone.HOST + "api.php/report/getReport";

    //还款通道签约通道
    /**
     * 签约通道列表
     */
    public static String ALL_QIANYUE_LIST = Clone.HOST + "api.php/user/getAllChannel";

    public static String WEB_URL=Clone.HOST+"api.php/extendApi/apiFdsxykc";

    public static String KEXINFEN_SHOP=Clone.HOST+"api.php/appModuleControl/appModuleControl";
    public static String HOME_YOUHUI=Clone.HOST+"api.php/user/upgradeLatest";



    public static String FENRUN_ZHANGHU=Clone.HOST+"api.php/user/splitterStatisticslist";

    public static String FENRUN_ZHANGHU_XIANGQING=Clone.HOST+"api.php/user/splitterDetail";
    public static String FRILV_MY=Clone.HOST+"api.php/user/myRate";
    public static String GOUMAI_XINGZHENGCHE=Clone.HOST+"api.php/user/teamupgrade";

    public static String YEJIGUALI_SHOW=Clone.HOST+"api.php/user/performancelist";
    public static String YEJIGUANLI_XIANGQING=Clone.HOST+"api.php/user/performanceDetail";
    public static String XIAOHONH_SHOW=Clone.HOST+"api.php/icon/iconShow2";
    public static String YONGHUFENRUNTIXIAN=Clone.HOST+"api.php/user/withdraw";
    public static String YONGHUFENXIANGPING=Clone.HOST+"api.php/user/withdrawRecord";


}

package com.patternStudy;

import java.util.Scanner;

public class TestMain {
	public static void main(String[] args) {
		int needDeliverySMS = 1;
		int needTicketSMS = 1;
		
		int isDeliverySMS = 0;
		int isTicketSMS = 0;
		String SMSText;
		
		Scanner scanner = new Scanner(System.in);
		//SMSText = scanner.next();
		
		/*【去哪儿网】飞机*/
//		SMSText = "已出票：订单『海航HU7597南京禄口机场T2-福州长乐机场 10月5日10:20-10月5日11:50翟丹丹,票"
//				+ "号:880-2171407321』请至少提前2小时到机场值机。【重要提示】请警惕以机械故障、航班取消等为由"
//				+ "的欺诈行为！退改及危险品携带托运及运输总条件详见http://d.qunar.com/1S4qZQ  如有问题，可"
//				+ "点击http://d.qunar.com/I2zgSI#  回T退订【去哪儿网】";
		/*【携程网】飞机*/
//		SMSText = "【携程网】已出票：订单4487720167『厦门航空 MF8212 银川河东机场T3-福州长乐机场 9月8日13:40"
//				+ "-9月8日18:25 龚昊，票号731-5731937155』请提前2小时至机场值机。"; 
		/*【南航】飞机*/
//		SMSText = "【南航】尊敬的旅客，您的机票已于2018-05-24支付成功。6月30日 CZ3445航班经济舱，广州(CAN)18"
//				+ ":10-上海虹桥(SHA)20:25，请前往机场南航柜台,提前45分钟完成办理乘机手续。乘机人丁楚仪，票号784"
//				+ "2349888768. 祝您旅途愉快。南航APP推出座位预选服务，戳 t.cn/RfjgZHh 。温馨提示：请阅读出行提示信息及运输总条件： m.csair.com/b/?Aw4fycj 。并认准南航官方客服电话95";
		/*【美团】飞机*/
//		SMSText = "【美团点评】您预订的2017-10-06 17:40，武汉天河机场T3航站楼-上海浦东机场T1航站楼的东航MU5372航班已成功出票，乘机人票号：陈红月(781-5723543466),夏鑫(781-5723543467)"
//				+ "，为避免误机，请提前2小时到机场办理乘机手续，如有疑问请致电美团点评网（10107888），祝您旅途愉快！为保证您的财产安全，请警惕以飞机故障、航班取消等内容的诈骗短信，勿o)。";
//		/*【飞猪提醒】飞机*/
//		SMSText = "【飞猪提醒】10-03 14:00羽田机场D2飞-10-03 15:05伊丹机场降全日空航空NH027，订单1256508639143(航空公司订单号：1808262003121b96fb6)已"
//				+ "成功，HOU/YETING,SHAO/CHANGYING,LI/WENXIN 票号205-2781287224/205-2781287226/205-2781287225。点击 tb.cn/wfd0ssw 查看"
//				+ "行程详细信息。请提前至少3小时到达机场办理登机。【重要提醒】近期诈骗猖狂，如您收到任何退改签、航班变动短信或电话，请联系卖家核实。卖家服务电话：0571-8"
//				+ "8139900， 飞猪服务电话：9510208 , 境外用户请拨打 86-0571-56888688。";
		/*【微快递】*/
//		SMSText = "【微快递】京东快递：您的京东包裹08538已到福州大学5区52楼京东站，双11站小库存大，请19点前及时领回15280099806";
		SMSText = "【微快递】｛京东快递｝您的京东包裹编码07008已到福州大学5区52号楼，快递中心京东站点，请14点前及时领取，谢谢15280099806";
		if (needDeliverySMS != 0 || needTicketSMS != 0) {
			SMSMatch test = new SMSMatch(SMSText);
			isDeliverySMS = test.getIsDeliverySMS();
			isTicketSMS = test.getIsTicketSMS();
			if (needDeliverySMS != 0 && isDeliverySMS != 0) {
				System.out.println("按要求输出快递短信关键字：");
				System.out.println(test.getKeyContent());
			}
			if (needTicketSMS != 0 && isTicketSMS != 0) {
				System.out.println("按要求输出车票短信关键字：");
				System.out.println(test.getKeyContent());			
			}
		} else {
			System.out.println("未开启短信分析功能");
		}
	}
}
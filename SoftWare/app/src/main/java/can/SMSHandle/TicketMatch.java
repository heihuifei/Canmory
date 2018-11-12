package com.patternStudy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketMatch {	
	private String SMSText;
	private int ticketType;
	
	private String ticketTime;
	private String ticketLocation;
	private String ticketDestination;
	private String ticketNeed;
	
	TicketMatch(String SMSText, int ticketType) {
		this.SMSText = SMSText;
		this.ticketType = ticketType;
		ticketTime = null;
		ticketLocation = null;
		ticketDestination = null;
		ticketNeed = null;
	}
	
	public String getKeyContent() {
		setKeyContent();
		return "出发时间：" + ticketTime + "\n" + "行程：" + ticketDestination 
				+ "\n" + "应携带：" + ticketNeed + "\n";
	}
	
	private void setKeyContent() {
		matchContent();
		if (ticketDestination != null) {
			ticketDestination = ticketLocation + "->" + ticketDestination;
		} else {
			ticketDestination = ticketLocation;
		}
		if (ticketNeed == null) {
			ticketNeed = "身份证或是购票证明";
		}
	}
	
	private void matchContent() {
		switch (ticketType) {
		case 101:
			matchType101();
			break;
		case 102:
			matchType102();
			break;
		case 103:
			matchType103();
			break;
		case 104:
			matchType104();
			break;
		case 105:
			matchType105();
			break;
		case 106:
			matchType106();
			break;
		case 107:
			matchType107();
			break;
		default:
			System.out.println("ERROOR:需要新增车票模板!");
		}
	}

	/**
	 * 【铁友网】
	 * 1. 汽车
	 */
	private void matchType101() {
		String regex = "[^\\】*]\\】([^\\,\\，]+)[\\,\\，]([^\\—\\-]+)"
				+ "[\\—\\-]([^\\,\\，]+)[\\,\\，].*凭(.+)取票.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(1);
			ticketLocation = m.group(2);
			ticketDestination = m.group(3);
			ticketNeed = m.group(4);
		} else {
			System.out.println("【铁友网】匹配失败");
		}
	}
	
	/**
	 * 【携程网】
	 * 1. 汽车
	 * 2. 飞机
	 */
	private void matchType102() {
		String regex;
		if (Pattern.matches(".*航空.*", SMSText)) {
			regex = ".*\\『([^\\-\\—]+)[\\—\\-]([\u4e00-\u9fcc]+)"
					+ "([0-9\\:年月日\\s\\-\\—]+).*[\\,\\，](.*)\\』.*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				ticketTime = m.group(3);
				ticketLocation = m.group(1);
				ticketDestination = m.group(2);
				ticketNeed = m.group(4);
			} else {
				System.out.println("【携程网】匹配失败");
			}
		} else {
			regex = "[^\\】*]\\】([0-9\\:年月日\\s]+)([^\\—\\-]+)[\\—\\-](.+)出票.*凭(.+)取票.*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				ticketTime = m.group(1);
				ticketLocation = m.group(2);
				ticketDestination = m.group(3);
				ticketNeed = m.group(4);
			} else {
				System.out.println("【携程网】匹配失败");
			}
		}
	}
	
	/**
	 * 【铁路客服】
	 * 1. 动车
	 */
	private void matchType103() {
		String regex = ".*已购([^A-Z]+)[^\\,\\，]*[\\,\\，]([\u4e00-\u9fcc]+)([^\u4e00-\u9fcc]+).*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(1) + m.group(3);
			ticketLocation = m.group(2);
			//ticketDestination = m.group(3);
			//ticketNeed = m.group(3);
		} else {
			System.out.println("【铁路客服】匹配失败");
		}
	}
	
	/**
	 * 【飞猪提醒】
	 * 1. 动车
	 * 2. 飞机
	 */
	private void matchType104() {
		String regex;
		if (Pattern.matches(".*航空.*", SMSText)) {
			regex = "[^\\】*]\\】([0-9\\:\\-\\—\\s]+)(.+?)飞[\\-\\—](.+?)降.*票号([^\\.\\。]+)[\\.\\。].*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				ticketTime = m.group(1);
				ticketLocation = m.group(1) + m.group(2);
				ticketDestination = m.group(3);
				ticketNeed = "票号" + m.group(4);
			} else {
				System.out.println("【飞猪提醒】匹配失败");
			}
		} else {
			regex = ".*出票成功[^\u4e00-\u9fcc]*([^\\—\\-]+)[\\—\\-]([\u4e00-\u9fcc]+)"
					+ ".*时间[^0-9年月日]*([0-9\\:年月日\\s]+).*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				ticketTime = m.group(3);
				ticketLocation = m.group(1);
				ticketDestination = m.group(2);
				//ticketNeed = m.group(4);
			} else {
				System.out.println("【飞猪提醒】匹配失败");
			}
		}
	}
	
	/**
	 * 【去哪儿网】
	 * 1. 飞机
	 */
	private void matchType105() {
		String regex = ".*\\『([^\\-\\—]+)[\\—\\-]([\u4e00-\u9fcc]+)([0-9\\:年月日\\s\\-\\—]+).*[\\,\\，](.*)\\』.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(3);
			ticketLocation = m.group(1);
			ticketDestination = m.group(2);
			ticketNeed = m.group(4);
		} else {
			System.out.println("【去哪儿网】匹配失败");
		}
	}
	
	/**
	 * 【南航】
	 * 1. 飞机
	 */
	private void matchType106() {
		String regex = ".*支付成功[^0-9月日]*([0-9年月日]+).*[\\,\\，]([^\\-\\—]+)[\\—\\-]([^\\,\\，]+).*票号([a-zA-Z0-9]+).*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(1);
			ticketLocation = m.group(2);
			ticketDestination = m.group(3);
			ticketNeed = "票号" + m.group(4);
		} else {
			System.out.println("【南航】匹配失败");
		}
	}
	
	/**
	 * 【美团点评】
	 * 1. 飞机
	 */
	private void matchType107() {
		String regex = ".*预[^0-9]*([0-9年月日\\:\\：\\s\\-\\—]+)[\\,\\，\\s]*([^\\-\\—]+)[\\-\\—](.+)的.*票号(.+?)[\\,\\，]{0,1}为避.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(1);
			ticketLocation = m.group(2);
			ticketDestination = m.group(3);
			ticketNeed = "票号" + m.group(4);
		} else {
			System.out.println("【美团点评】匹配失败");
		}
	}
}
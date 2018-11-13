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
		return "����ʱ�䣺" + ticketTime + "\n" + "�г̣�" + ticketDestination 
				+ "\n" + "ӦЯ����" + ticketNeed + "\n";
	}
	
	private void setKeyContent() {
		matchContent();
		if (ticketDestination != null) {
			ticketDestination = ticketLocation + "->" + ticketDestination;
		} else {
			ticketDestination = ticketLocation;
		}
		if (ticketNeed == null) {
			ticketNeed = "���֤���ǹ�Ʊ֤��";
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
			System.out.println("ERROOR:��Ҫ������Ʊģ��!");
		}
	}

	/**
	 * ����������
	 * 1. ����
	 */
	private void matchType101() {
		String regex = "[^\\��*]\\��([^\\,\\��]+)[\\,\\��]([^\\��\\-]+)"
				+ "[\\��\\-]([^\\,\\��]+)[\\,\\��].*ƾ(.+)ȡƱ.*";
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
			System.out.println("����������ƥ��ʧ��");
		}
	}
	
	/**
	 * ��Я������
	 * 1. ����
	 * 2. �ɻ�
	 */
	private void matchType102() {
		String regex;
		if (Pattern.matches(".*����.*", SMSText)) {
			regex = ".*\\��([^\\-\\��]+)[\\��\\-]([\u4e00-\u9fcc]+)"
					+ "([0-9\\:������\\s\\-\\��]+).*[\\,\\��](.*)\\��.*";
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
				System.out.println("��Я������ƥ��ʧ��");
			}
		} else {
			regex = "[^\\��*]\\��([0-9\\:������\\s]+)([^\\��\\-]+)[\\��\\-](.+)��Ʊ.*ƾ(.+)ȡƱ.*";
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
				System.out.println("��Я������ƥ��ʧ��");
			}
		}
	}
	
	/**
	 * ����·�ͷ���
	 * 1. ����
	 */
	private void matchType103() {
		String regex = ".*�ѹ�([^A-Z]+)[^\\,\\��]*[\\,\\��]([\u4e00-\u9fcc]+)([^\u4e00-\u9fcc]+).*";
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
			System.out.println("����·�ͷ���ƥ��ʧ��");
		}
	}
	
	/**
	 * ���������ѡ�
	 * 1. ����
	 * 2. �ɻ�
	 */
	private void matchType104() {
		String regex;
		if (Pattern.matches(".*����.*", SMSText)) {
			regex = "[^\\��*]\\��([0-9\\:\\-\\��\\s]+)(.+?)��[\\-\\��](.+?)��.*Ʊ��([^\\.\\��]+)[\\.\\��].*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				ticketTime = m.group(1);
				ticketLocation = m.group(1) + m.group(2);
				ticketDestination = m.group(3);
				ticketNeed = "Ʊ��" + m.group(4);
			} else {
				System.out.println("���������ѡ�ƥ��ʧ��");
			}
		} else {
			regex = ".*��Ʊ�ɹ�[^\u4e00-\u9fcc]*([^\\��\\-]+)[\\��\\-]([\u4e00-\u9fcc]+)"
					+ ".*ʱ��[^0-9������]*([0-9\\:������\\s]+).*";
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
				System.out.println("���������ѡ�ƥ��ʧ��");
			}
		}
	}
	
	/**
	 * ��ȥ�Ķ�����
	 * 1. �ɻ�
	 */
	private void matchType105() {
		String regex = ".*\\��([^\\-\\��]+)[\\��\\-]([\u4e00-\u9fcc]+)([0-9\\:������\\s\\-\\��]+).*[\\,\\��](.*)\\��.*";
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
			System.out.println("��ȥ�Ķ�����ƥ��ʧ��");
		}
	}
	
	/**
	 * ���Ϻ���
	 * 1. �ɻ�
	 */
	private void matchType106() {
		String regex = ".*֧���ɹ�[^0-9����]*([0-9������]+).*[\\,\\��]([^\\-\\��]+)[\\��\\-]([^\\,\\��]+).*Ʊ��([a-zA-Z0-9]+).*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(1);
			ticketLocation = m.group(2);
			ticketDestination = m.group(3);
			ticketNeed = "Ʊ��" + m.group(4);
		} else {
			System.out.println("���Ϻ���ƥ��ʧ��");
		}
	}
	
	/**
	 * �����ŵ�����
	 * 1. �ɻ�
	 */
	private void matchType107() {
		String regex = ".*Ԥ[^0-9]*([0-9������\\:\\��\\s\\-\\��]+)[\\,\\��\\s]*([^\\-\\��]+)[\\-\\��](.+)��.*Ʊ��(.+?)[\\,\\��]{0,1}Ϊ��.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			ticketTime = m.group(1);
			ticketLocation = m.group(2);
			ticketDestination = m.group(3);
			ticketNeed = "Ʊ��" + m.group(4);
		} else {
			System.out.println("�����ŵ�����ƥ��ʧ��");
		}
	}
}
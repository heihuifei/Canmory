package com.patternStudy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Ϊ�û��ṩ����ӿ�
 * main���������Ķ���->ƥ���������ֵ->��������ֵ����������ʽ����->���ݱ����������ַ�����main
 * ʵ��������->.getIsSpecialSMS();--���Ϊtrue-->.getKeyContent();
 */
public class SMSMatch {
	private String SMSText;
	private boolean needPattern;
	private int type;
	private String keyContent;
	private String title;
	private String endTime;
	private boolean hasJudgeSMS;
	private int isTicketSMS;
	private int isDeliverySMS;
	private boolean hasHandleSMS;
	
	/*
	 * ��ݶ�������ֵ��1-100
	 */
	private static final String TYPE1 = "������վ";
	private static final String TYPE2 = "������";
	private static final String TYPE3 = "EMS���";
	private static final String TYPE4 = "������վ";
	private static final String TYPE5 = "˳������";
	private static final String TYPE6 = "΢���";	
	private static final String TYPE7 = "˳����";
	private static final String TYPE8 = "�ᳲ";
	/*
	 * ��Ʊ��������ֵ��101-...
	 */
	private static final String TYPE101 = "������";
	private static final String TYPE102 = "Я����";
	private static final String TYPE103 = "��·�ͷ�";
	private static final String TYPE104 = "��������";
	private static final String TYPE105 = "ȥ�Ķ���";
	private static final String TYPE106 = "�Ϻ�";
	private static final String TYPE107 = "���ŵ���";
	
	SMSMatch(String SMSText) {
		this.SMSText = SMSText;
		hasJudgeSMS = false;
		needPattern = true;
		keyContent = null;
		title = null;
		endTime = null;
		type = 0;
		isDeliverySMS = 0;
		isTicketSMS = 0;
		hasHandleSMS = false;
	}
	
	/**
	 * ���ظ���˶��ŵ�����
	 * @return 
	 * 	0����ͨ����
	 * 	1����ݶ���
	 * 	2����Ʊ����
	 */
	public int getIsTicketSMS() {
		if (!hasJudgeSMS) {
			setIsSpecialSMS();
		}
		return isTicketSMS;
	}
	
	public int getIsDeliverySMS() {
		if (!hasJudgeSMS) {
			setIsSpecialSMS();
		}
		return isDeliverySMS;
	}
	
	/**
	 * String��ʽ����Ӧ���ɵı���¼����
	 * @return ����¼����
	 */
	public String getKeyContent() {
		setKeyContent();
		return keyContent;
	}
	
	private void setIsSpecialSMS() {
		judgeSMSContent();
	}
	
	/**
	 * �ж��Ƿ�Ϊ�������
	 */
	private void judgeSMSContent() {
		hasJudgeSMS = true;
		if (needPattern) {
			choseType();
		}
		if (type == 0) {
			isDeliverySMS = 0;
			isTicketSMS = 0;
		} else if (type < 100) {
			isDeliverySMS = 1;
			isTicketSMS = 0;
		} else if (type > 100) {
			isDeliverySMS = 0;
			isTicketSMS = 1;
		}
	}		

	/**
	 * Ϊ����ѡ����ȷ����ֵ
	 */
	private void choseType() {
		needPattern = false;
		String regex = ".*?\\��(.*?)\\��.*";										//����ƥ�����ȳ��ֵġ����е��ַ�
		Matcher matcher = Pattern.compile(regex).matcher(SMSText);
		if (!matcher.find() || Pattern.matches(".*��[����]�ܾ�.*", SMSText) || 
				Pattern.matches(".*����ѡ��.*", SMSText)) {
			System.out.println("�����е��ַ�����");
			type = 0;
		} else {
			String typeJudge = matcher.group(1);
			//System.out.println(typeJudge);
			judgeType(typeJudge);
		}
	}
	
	/**
	 * Ϊ�������ƥ������ֵ
	 * @param typeJudge
	 */
	private void judgeType(String typeJudge) {
		//��ݶ�������ֵ
		if (typeJudge.equals(TYPE1)) {
			type = 1;
		} else if (typeJudge.equals(TYPE2)) {
			type = 2;
		} else if (typeJudge.equals(TYPE3)) {
			type = 3;
		} else if (typeJudge.equals(TYPE4)) {
			type = 4;
		} else if (typeJudge.equals(TYPE5)) {
			type = 5;
		} else if (typeJudge.equals(TYPE6)) {
			type = 6;
		} else if (typeJudge.equals(TYPE7)) {
			type = 7;
		} else if (typeJudge.equals(TYPE8)) {
			type = 8;
		//��Ʊ��Ϣ����ֵ
		} else if (typeJudge.equals(TYPE101)) {
			type = 101;
		} else if (typeJudge.equals(TYPE102)) {
			type = 102;
		} else if (typeJudge.equals(TYPE103)) {
			type = 103;
		} else if (typeJudge.equals(TYPE104)) {
			type = 104;
		} else if (typeJudge.equals(TYPE105)) {
			type = 105;
		} else if (typeJudge.equals(TYPE106)) {
			type = 106;
		} else if (typeJudge.equals(TYPE107)) {
			type = 107;
		} else {
			type = 0;
		}
	}

	private void setKeyContent() {
		if (hasHandleSMS == false) {
			handleSMS();
		}
	}
	
	private void setTitle() {
		if (hasHandleSMS == false) {
			handleSMS();
		}
	}
	
	private void setEndTime() {
		if (hasHandleSMS == false) {
			handleSMS();
		}
		int curMonth;
		int endMonth;
		int curYear;
		String formatYear;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String curTime = formatter.format(curDate);
		//System.out.println(curTime);
		Matcher yearMatch = Pattern.compile("(.*?)\\-(.*?)\\-.*").matcher(curTime);
		Matcher dateMatch = Pattern.compile("(.*?)\\-.*").matcher(endTime);
		if (yearMatch.find() && dateMatch.find()) {
			curMonth = Integer.parseInt(yearMatch.group(2));
			endMonth = Integer.parseInt(dateMatch.group(1));
			formatYear = yearMatch.group(1);
			if (curMonth > endMonth) {
				curYear = Integer.parseInt(formatYear) + 1;
				formatYear = String.valueOf(curYear);
			}
			endTime = formatYear + "-" + endTime;
		} else {
			System.out.println("ʱ�䴦������\"yyyy-\"��ʽ����");
		}
	}
	
	private void handleSMS() {
		if (hasHandleSMS == false) {
			if (type <= 0) {
				keyContent = "ERROR:ʶ����Ϊ��ͨ����!";
			} else if (type < 100) {
				DeliveryMatch deliveryMatch = new DeliveryMatch(SMSText, type);
				keyContent = deliveryMatch.getKeyContent();
				title = deliveryMatch.getTitle();
			} else if (type > 100) {
				TicketMatch ticketMatch = new TicketMatch(SMSText, type);
				keyContent = ticketMatch.getKeyContent();
				title = ticketMatch.getTitle();
				endTime = ticketMatch.getEndTime();
			}
			hasHandleSMS = true;
		}
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		setTitle();
		return title;
	}
	
	public String getTicketEndTime() {
		setEndTime();
		return endTime;
	}
}

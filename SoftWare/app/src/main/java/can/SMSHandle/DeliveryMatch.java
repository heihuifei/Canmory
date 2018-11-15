package com.patternStudy;

/**
 * finished
 * 
 * DeliveryMatch������ʶ���ݶ���
 * �ȵ���getIsDelivery()�����Ƿ��ݶ���(ͨ��ƥ�䡰��ݡ��͡�ȡ���롱�����ؼ����ж�)
 * ��ͨ�����к����ӿڷ�������ؼ�����Ϣ(��˾���ƣ�ȡ��ʱ�䣬ȡ�����裬ȡ����ַ)
 * 
 * ͨ��������ʽ
 * 	ƥ���������һ���ַ���[^\\��*]\\��
 * 	ƥ��xx��ݵ�xx(����û�п������)��([^���]+)[���]{0,2}
 * 	ƥ��һ�������ַ���[\u4e00-\u9fcc]
 * 	ƥ�䶺��(,�ͣ�)��[\\,\\��]
 * 	ƥ������(0-9):[0-9]
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryMatch {
	private String SMSText;
	private int deliveryType;
	
	private String companyName;
	private String deliveryTime;
	private String deliveryLocation;
	private String deliveryNeed;

	DeliveryMatch(String SMSText, int deliveryType) {
		this.SMSText = SMSText;
		this.deliveryType = deliveryType;
		companyName = null;
		deliveryTime = null;
		deliveryLocation = null;
		deliveryNeed = null;
	}
	
	public String getKeyContent() {
		setKeyContent();
		return "������ƣ�" + companyName + "\n" + "ȡ��ʱ�䣺" + deliveryTime + "\n"
				+ "ȡ���ص㣺" + deliveryLocation + "\n" + "ȡ�����裺" + deliveryNeed + "\n";
	}

	private void setKeyContent() {
		matchContent();
		if (deliveryNeed == null) {
			deliveryNeed = "�������Ż��ռ����ֻ�β��";
		}
		if (deliveryTime == null) {
			deliveryTime = "����ʱ����";
		}
	}
	
	private void matchContent() {
		switch (deliveryType) {
		case 1:
			matchType1();		//������վ
			break;
		case 2:
			matchType2();		//������
			break;
		case 3:
			matchType3();		//ems���
			break;
		case 4:
			matchType4();		//������վ
			break;
		case 5:
			matchType5();		//˳������
			break;
		case 6:
			matchType6();		//΢���
			break;
		case 7:
			matchType7();		//˳����
			break;
		case 8:
			matchType8();		//�ᳲ
			break;
		default:
			System.out.println("ERROOR:��Ҫ�������ģ��!");
		}
	}
	
	/**
	 * ƥ�䡾������վ��
	 */
	private void matchType1() {
		String regex = "[^\\��*]\\��[����]{0,2}([^���]+)[���]{0,2}����"
				+ "��([^\\,\\��]+)" + "[\\,\\��]��(.*)ƾ(.*)ȡ.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			// System.out.println(m.group(0));
			companyName = m.group(1) + "���";
			deliveryTime = m.group(3);
			deliveryLocation = m.group(2);
			deliveryNeed = "ȡ����--" + m.group(4);
		} else {
			System.out.println("��������վ��ƥ��ʧ��");
		}
	}

	/**
	 * ƥ�䡾�����ȡ�
	 */
	private void matchType2() {
		String regex;
		if (!Pattern.matches(".*����.*", SMSText)) {
			regex = "[^\\��*]\\�����([0-9]*)([^���]+)[���]"
					+ "{0,2}����([^\u4e00-\u9fcc]+)(.*)��ȡ.*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				companyName = m.group(2) + "���";
				deliveryTime = m.group(3);
				deliveryLocation = m.group(4);
				deliveryNeed = "ȡ����--" + m.group(1);
			} else {
				System.out.println("�������ȡ�ƥ��ʧ��");
			}
		} else {
			regex = ".*���([0-9a-zA-Z]+).*��([^\\,\\����]+).*��(.*)ȡ.*";
			Pattern p = Pattern.compile(regex);
			//System.out.println(p.pattern());
			Matcher m = p.matcher(SMSText);
			if (m.find()) {
				//System.out.println(m.group(0));
				companyName = "�����׹�";
				deliveryTime = m.group(2);
				deliveryLocation = m.group(3);
				deliveryNeed = "ȡ����--" + m.group(1);
			} else {
				System.out.println("�������ȡ�ƥ��ʧ��");
			}
		}
	}
	
	/**
	 * ƥ�䡾EMS��ݡ�
	 */
	private void matchType3() {
		String regex = "[^\\��*]\\��([^\\,\\��]*)[\\,\\��](.*)��(.*)��.*��(.*)��.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			companyName = m.group(1) + "���";
			deliveryTime = m.group(2);
			deliveryLocation = m.group(3);
			deliveryNeed = "ȡ����--" + m.group(4);
		} else {
			System.out.println("��EMS��ݡ�ƥ��ʧ��");
		}
	}

	/**
	 * ƥ�䡾������վ��
	 */
	private void matchType4() {
		String regex = "[^\\��*]\\��ȡ����([0-9]*)[\\,\\������]{0,3}([^���]+)"
				+ "���[����]{0,2}[\\��\\,�ѵ�]{0,3}([0-9\u4e00-\u9fcc]+).*ʱ��"
				+ "([0-9\\:\\��\\-\\��]+).*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			companyName = m.group(2) + "���";
			deliveryTime = m.group(4);
			deliveryLocation = m.group(3);
			deliveryNeed = "ȡ����--" + m.group(1);
		} else {
			System.out.println("��������վ��ƥ��ʧ��");
		}
	}	

	/**
	 * ƥ�䡾˳�����ˡ�
	 */
	private void matchType5() {
		String regex = ".*������([^\\��||\\,]+).*���(.*)ǰ��.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			companyName = "˳������";
			deliveryLocation = m.group(1);
			deliveryNeed = m.group(2);
		} else {
			System.out.println("��˳�����ˡ�ƥ��ʧ��");
		}
	}
	
	/**
	 * ƥ�䡾΢��ݡ�
	 */
	private void matchType6() {
		//String regex = ".*\\��([^\\��]+).*����([0-9]+).*��([^\\,||\\��]*).*��(.*)��ʱ.*";
		String regex = "[^\\��*]\\��[^\u4e00-\u9fcc]*([\u4e00-\u9fcc]+)[^\u4e00-\u9fcc]*.*����[����]*([0-9]+).*��([^\\,||\\��]*).*��(.*)��ʱ.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			companyName = m.group(1);
			deliveryTime = m.group(4);
			deliveryLocation = m.group(3);
			deliveryNeed = m.group(2);
		} else {
			System.out.println("��΢��ݡ�ƥ��ʧ��");
		}
	}

	/**
	 * ƥ�䡾˳���ݡ�
	 */
	private void matchType7() {
		String regex = ".*��(.*)��(.*)��ȡ.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			companyName = "˳����";
			deliveryTime = m.group(1);
			deliveryLocation = m.group(2);
		} else {
			System.out.println("��˳�����ˡ�ƥ��ʧ��");
		}
	}
	
	/**
	 * ƥ�䡾�ᳲ��
	 */
	private void matchType8() {
		String regex = ".*ȡ����\\��{0,1}([0-9]+)\\��{0,1}��(.*)ȡ(.*)��.*";
		Pattern p = Pattern.compile(regex);
		//System.out.println(p.pattern());
		Matcher m = p.matcher(SMSText);
		if (m.find()) {
			//System.out.println(m.group(0));
			companyName = m.group(3);
			//deliveryTime = m.group(4);
			deliveryLocation = m.group(2);
			deliveryNeed = "ȡ����--" + m.group(1);
		} else {
			System.out.println("��΢��ݡ�ƥ��ʧ��");
		}
	}
}
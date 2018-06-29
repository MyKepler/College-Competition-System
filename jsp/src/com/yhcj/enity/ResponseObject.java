package com.yhcj.enity;

/**
 * ������ķ�װ
 *
 */
public class ResponseObject {
	
	private String msg;  //����˷��ظ��ͻ��˵���Ϣ
	private int state = 1; //0 ��ʾ��������ʧ��
	private Object datas; //����������õĽ�������
	private int page;
	private int size;
	private int count;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	//ʧ�ܵ�ʱ�򷵻�ʧ����Ϣ���Լ�state
	public ResponseObject(int state, String msg) {
		this.state = state;
		this.msg = msg;
	}
	//�ɹ���ʱ��
	public ResponseObject(int state, Object datas) {
		this.state = state;
		this.datas = datas;
	}
	public ResponseObject(int state, String msg, Object datas) {
		this.state = state;
		this.msg = msg;
		this.datas = datas;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Object getDatas() {
		return datas;
	}
	public void setDatas(Object datas) {
		this.datas = datas;
	}
}

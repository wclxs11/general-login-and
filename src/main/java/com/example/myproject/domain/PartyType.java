package com.example.myproject.domain;


public enum PartyType{
	YueFan("约饭"),YueQiu("约球"),YueKTV("约KTV"),YueKaiHei("约开黑"),Qita("其他");
	String type;
	private PartyType(String type){
		this.type = type;
	}
}

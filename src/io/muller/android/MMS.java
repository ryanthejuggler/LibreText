package io.muller.android;

public class MMS extends Message {
	public static MMS getByID(String _id){
		MMS self = new MMS();
		self.setID(_id);
		return self;
	}
	public String getSimplePreview(){
		return this.getBody();
	}
}

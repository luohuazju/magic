package com.sillycat.magicneptune.model;



/**
 * shop
 * @author SILLYCAT
 *
 */
public class MerchantUser extends BasicUser {

	private static final long serialVersionUID = -7621607465147135984L;

	private String merchantname;
	
	private String website;
	
	/**
	 * 1=active, 2=delete, 4=block, 8=VIP, 16=candidate
	 */
	private int status;
	
	public String getMerchantname() {
		return merchantname;
	}

	public void setMerchantname(String merchantname) {
		this.merchantname = merchantname;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	} 
	
}

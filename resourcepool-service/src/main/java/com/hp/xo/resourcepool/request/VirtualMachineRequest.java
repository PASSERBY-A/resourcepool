package com.hp.xo.resourcepool.request;

import com.hp.xo.resourcepool.model.Parameter;
import com.hp.xo.resourcepool.request.BaseRequest.FieldType;

public class VirtualMachineRequest extends BuessionListRequest{
	@Parameter(name="expunge", type=FieldType.BOOLEAN, description="")
	private boolean expunge;

	public boolean isExpunge() {
		return expunge;
	}

	public void setExpunge(boolean expunge) {
		this.expunge = expunge;
	}
	
}

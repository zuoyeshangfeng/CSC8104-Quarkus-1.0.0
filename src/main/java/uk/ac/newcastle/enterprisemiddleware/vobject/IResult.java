package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


public class IResult implements Serializable {
    private static final long serialVersionUID = -4060684554969148509L;
    @ApiModelProperty("result")
    private String result;

    @ApiModelProperty("return message")
    private String message;

    public IResult(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

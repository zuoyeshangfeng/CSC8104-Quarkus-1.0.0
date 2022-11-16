package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class QueryCustomerListResult extends IResult {
    private static final long serialVersionUID = -2604223241198497370L;

    @ApiModelProperty("customer list")
    private List<CustomerVO> customerList;

    public QueryCustomerListResult(String result, String message, List<CustomerVO> customerList) {
        super(result, message);
        this.customerList = customerList;
    }

    public List<CustomerVO> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerVO> customerList) {
        this.customerList = customerList;
    }
}

package uk.ac.newcastle.enterprisemiddleware.vobject;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class QueryFlightListResult extends IResult{
    private static final long serialVersionUID = -322350842294694945L;

    @ApiModelProperty("flight list")
    private List<FlightVO> flightVOList;

    public QueryFlightListResult(String result, String message, List<FlightVO> flightVOList) {
        super(result, message);
        this.flightVOList = flightVOList;
    }

    public List<FlightVO> getFlightVOList() {
        return flightVOList;
    }

    public void setFlightVOList(List<FlightVO> flightVOList) {
        this.flightVOList = flightVOList;
    }
}

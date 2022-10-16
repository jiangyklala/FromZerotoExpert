package com.jiang.fzte.domain;

public class RecordLog {
    private Long opTime;

    private String opAc;

    private String status;

    private String opType;

    private String reqUrl;

    private String reqMtd;

    private String opDesc;

    private String errMsg;

    private String timeCsm;

    private String opIp;

    public Long getOpTime() {
        return opTime;
    }

    public void setOpTime(Long opTime) {
        this.opTime = opTime;
    }

    public String getOpAc() {
        return opAc;
    }

    public void setOpAc(String opAc) {
        this.opAc = opAc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getReqMtd() {
        return reqMtd;
    }

    public void setReqMtd(String reqMtd) {
        this.reqMtd = reqMtd;
    }

    public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getTimeCsm() {
        return timeCsm;
    }

    public void setTimeCsm(String timeCsm) {
        this.timeCsm = timeCsm;
    }

    public String getOpIp() {
        return opIp;
    }

    public void setOpIp(String opIp) {
        this.opIp = opIp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", opTime=").append(opTime);
        sb.append(", opAc=").append(opAc);
        sb.append(", status=").append(status);
        sb.append(", opType=").append(opType);
        sb.append(", reqUrl=").append(reqUrl);
        sb.append(", reqMtd=").append(reqMtd);
        sb.append(", opDesc=").append(opDesc);
        sb.append(", errMsg=").append(errMsg);
        sb.append(", timeCsm=").append(timeCsm);
        sb.append(", opIp=").append(opIp);
        sb.append("]");
        return sb.toString();
    }
}
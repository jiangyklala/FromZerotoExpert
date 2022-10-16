package com.jiang.fzte.domain;

import java.util.ArrayList;
import java.util.List;

public class LogHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LogHistoryExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andOpTimeIsNull() {
            addCriterion("op_time is null");
            return (Criteria) this;
        }

        public Criteria andOpTimeIsNotNull() {
            addCriterion("op_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpTimeEqualTo(Long value) {
            addCriterion("op_time =", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotEqualTo(Long value) {
            addCriterion("op_time <>", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThan(Long value) {
            addCriterion("op_time >", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("op_time >=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThan(Long value) {
            addCriterion("op_time <", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThanOrEqualTo(Long value) {
            addCriterion("op_time <=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeIn(List<Long> values) {
            addCriterion("op_time in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotIn(List<Long> values) {
            addCriterion("op_time not in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeBetween(Long value1, Long value2) {
            addCriterion("op_time between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotBetween(Long value1, Long value2) {
            addCriterion("op_time not between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpAcIsNull() {
            addCriterion("op_ac is null");
            return (Criteria) this;
        }

        public Criteria andOpAcIsNotNull() {
            addCriterion("op_ac is not null");
            return (Criteria) this;
        }

        public Criteria andOpAcEqualTo(String value) {
            addCriterion("op_ac =", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcNotEqualTo(String value) {
            addCriterion("op_ac <>", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcGreaterThan(String value) {
            addCriterion("op_ac >", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcGreaterThanOrEqualTo(String value) {
            addCriterion("op_ac >=", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcLessThan(String value) {
            addCriterion("op_ac <", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcLessThanOrEqualTo(String value) {
            addCriterion("op_ac <=", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcLike(String value) {
            addCriterion("op_ac like", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcNotLike(String value) {
            addCriterion("op_ac not like", value, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcIn(List<String> values) {
            addCriterion("op_ac in", values, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcNotIn(List<String> values) {
            addCriterion("op_ac not in", values, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcBetween(String value1, String value2) {
            addCriterion("op_ac between", value1, value2, "opAc");
            return (Criteria) this;
        }

        public Criteria andOpAcNotBetween(String value1, String value2) {
            addCriterion("op_ac not between", value1, value2, "opAc");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("`status` like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("`status` not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andOpTypeIsNull() {
            addCriterion("op_type is null");
            return (Criteria) this;
        }

        public Criteria andOpTypeIsNotNull() {
            addCriterion("op_type is not null");
            return (Criteria) this;
        }

        public Criteria andOpTypeEqualTo(String value) {
            addCriterion("op_type =", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotEqualTo(String value) {
            addCriterion("op_type <>", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeGreaterThan(String value) {
            addCriterion("op_type >", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeGreaterThanOrEqualTo(String value) {
            addCriterion("op_type >=", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeLessThan(String value) {
            addCriterion("op_type <", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeLessThanOrEqualTo(String value) {
            addCriterion("op_type <=", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeLike(String value) {
            addCriterion("op_type like", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotLike(String value) {
            addCriterion("op_type not like", value, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeIn(List<String> values) {
            addCriterion("op_type in", values, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotIn(List<String> values) {
            addCriterion("op_type not in", values, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeBetween(String value1, String value2) {
            addCriterion("op_type between", value1, value2, "opType");
            return (Criteria) this;
        }

        public Criteria andOpTypeNotBetween(String value1, String value2) {
            addCriterion("op_type not between", value1, value2, "opType");
            return (Criteria) this;
        }

        public Criteria andReqUrlIsNull() {
            addCriterion("req_url is null");
            return (Criteria) this;
        }

        public Criteria andReqUrlIsNotNull() {
            addCriterion("req_url is not null");
            return (Criteria) this;
        }

        public Criteria andReqUrlEqualTo(String value) {
            addCriterion("req_url =", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotEqualTo(String value) {
            addCriterion("req_url <>", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlGreaterThan(String value) {
            addCriterion("req_url >", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlGreaterThanOrEqualTo(String value) {
            addCriterion("req_url >=", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLessThan(String value) {
            addCriterion("req_url <", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLessThanOrEqualTo(String value) {
            addCriterion("req_url <=", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlLike(String value) {
            addCriterion("req_url like", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotLike(String value) {
            addCriterion("req_url not like", value, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlIn(List<String> values) {
            addCriterion("req_url in", values, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotIn(List<String> values) {
            addCriterion("req_url not in", values, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlBetween(String value1, String value2) {
            addCriterion("req_url between", value1, value2, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqUrlNotBetween(String value1, String value2) {
            addCriterion("req_url not between", value1, value2, "reqUrl");
            return (Criteria) this;
        }

        public Criteria andReqMtdIsNull() {
            addCriterion("req_mtd is null");
            return (Criteria) this;
        }

        public Criteria andReqMtdIsNotNull() {
            addCriterion("req_mtd is not null");
            return (Criteria) this;
        }

        public Criteria andReqMtdEqualTo(String value) {
            addCriterion("req_mtd =", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdNotEqualTo(String value) {
            addCriterion("req_mtd <>", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdGreaterThan(String value) {
            addCriterion("req_mtd >", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdGreaterThanOrEqualTo(String value) {
            addCriterion("req_mtd >=", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdLessThan(String value) {
            addCriterion("req_mtd <", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdLessThanOrEqualTo(String value) {
            addCriterion("req_mtd <=", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdLike(String value) {
            addCriterion("req_mtd like", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdNotLike(String value) {
            addCriterion("req_mtd not like", value, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdIn(List<String> values) {
            addCriterion("req_mtd in", values, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdNotIn(List<String> values) {
            addCriterion("req_mtd not in", values, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdBetween(String value1, String value2) {
            addCriterion("req_mtd between", value1, value2, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andReqMtdNotBetween(String value1, String value2) {
            addCriterion("req_mtd not between", value1, value2, "reqMtd");
            return (Criteria) this;
        }

        public Criteria andOpDescIsNull() {
            addCriterion("op_desc is null");
            return (Criteria) this;
        }

        public Criteria andOpDescIsNotNull() {
            addCriterion("op_desc is not null");
            return (Criteria) this;
        }

        public Criteria andOpDescEqualTo(String value) {
            addCriterion("op_desc =", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescNotEqualTo(String value) {
            addCriterion("op_desc <>", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescGreaterThan(String value) {
            addCriterion("op_desc >", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescGreaterThanOrEqualTo(String value) {
            addCriterion("op_desc >=", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescLessThan(String value) {
            addCriterion("op_desc <", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescLessThanOrEqualTo(String value) {
            addCriterion("op_desc <=", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescLike(String value) {
            addCriterion("op_desc like", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescNotLike(String value) {
            addCriterion("op_desc not like", value, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescIn(List<String> values) {
            addCriterion("op_desc in", values, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescNotIn(List<String> values) {
            addCriterion("op_desc not in", values, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescBetween(String value1, String value2) {
            addCriterion("op_desc between", value1, value2, "opDesc");
            return (Criteria) this;
        }

        public Criteria andOpDescNotBetween(String value1, String value2) {
            addCriterion("op_desc not between", value1, value2, "opDesc");
            return (Criteria) this;
        }

        public Criteria andErrMsgIsNull() {
            addCriterion("err_msg is null");
            return (Criteria) this;
        }

        public Criteria andErrMsgIsNotNull() {
            addCriterion("err_msg is not null");
            return (Criteria) this;
        }

        public Criteria andErrMsgEqualTo(String value) {
            addCriterion("err_msg =", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgNotEqualTo(String value) {
            addCriterion("err_msg <>", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgGreaterThan(String value) {
            addCriterion("err_msg >", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgGreaterThanOrEqualTo(String value) {
            addCriterion("err_msg >=", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgLessThan(String value) {
            addCriterion("err_msg <", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgLessThanOrEqualTo(String value) {
            addCriterion("err_msg <=", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgLike(String value) {
            addCriterion("err_msg like", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgNotLike(String value) {
            addCriterion("err_msg not like", value, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgIn(List<String> values) {
            addCriterion("err_msg in", values, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgNotIn(List<String> values) {
            addCriterion("err_msg not in", values, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgBetween(String value1, String value2) {
            addCriterion("err_msg between", value1, value2, "errMsg");
            return (Criteria) this;
        }

        public Criteria andErrMsgNotBetween(String value1, String value2) {
            addCriterion("err_msg not between", value1, value2, "errMsg");
            return (Criteria) this;
        }

        public Criteria andTimeCsmIsNull() {
            addCriterion("time_csm is null");
            return (Criteria) this;
        }

        public Criteria andTimeCsmIsNotNull() {
            addCriterion("time_csm is not null");
            return (Criteria) this;
        }

        public Criteria andTimeCsmEqualTo(String value) {
            addCriterion("time_csm =", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmNotEqualTo(String value) {
            addCriterion("time_csm <>", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmGreaterThan(String value) {
            addCriterion("time_csm >", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmGreaterThanOrEqualTo(String value) {
            addCriterion("time_csm >=", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmLessThan(String value) {
            addCriterion("time_csm <", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmLessThanOrEqualTo(String value) {
            addCriterion("time_csm <=", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmLike(String value) {
            addCriterion("time_csm like", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmNotLike(String value) {
            addCriterion("time_csm not like", value, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmIn(List<String> values) {
            addCriterion("time_csm in", values, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmNotIn(List<String> values) {
            addCriterion("time_csm not in", values, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmBetween(String value1, String value2) {
            addCriterion("time_csm between", value1, value2, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andTimeCsmNotBetween(String value1, String value2) {
            addCriterion("time_csm not between", value1, value2, "timeCsm");
            return (Criteria) this;
        }

        public Criteria andOpIpIsNull() {
            addCriterion("op_ip is null");
            return (Criteria) this;
        }

        public Criteria andOpIpIsNotNull() {
            addCriterion("op_ip is not null");
            return (Criteria) this;
        }

        public Criteria andOpIpEqualTo(String value) {
            addCriterion("op_ip =", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpNotEqualTo(String value) {
            addCriterion("op_ip <>", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpGreaterThan(String value) {
            addCriterion("op_ip >", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpGreaterThanOrEqualTo(String value) {
            addCriterion("op_ip >=", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpLessThan(String value) {
            addCriterion("op_ip <", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpLessThanOrEqualTo(String value) {
            addCriterion("op_ip <=", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpLike(String value) {
            addCriterion("op_ip like", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpNotLike(String value) {
            addCriterion("op_ip not like", value, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpIn(List<String> values) {
            addCriterion("op_ip in", values, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpNotIn(List<String> values) {
            addCriterion("op_ip not in", values, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpBetween(String value1, String value2) {
            addCriterion("op_ip between", value1, value2, "opIp");
            return (Criteria) this;
        }

        public Criteria andOpIpNotBetween(String value1, String value2) {
            addCriterion("op_ip not between", value1, value2, "opIp");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
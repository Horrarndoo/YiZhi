package com.zyw.horrarndoo.yizhi.model.bean.weixin;

import java.util.List;

/**
 * Created by Horrarndoo on 2017/9/21.
 * <p>
 */

public class WeixinChoiceListBean {
    private String reason;
    private Result result;
    private String error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WeixinChoiceListBean{" +
                "reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code='" + error_code + '\'' +
                '}';
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public class Result {
        private List<WeixinChoiceItemBean> list;
        private String totalPage;
        private String ps;
        private String pno;

        public List<WeixinChoiceItemBean> getList() {
            return list;
        }

        public void setList(List<WeixinChoiceItemBean> list) {
            this.list = list;
        }

        public String getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(String totalPage) {
            this.totalPage = totalPage;
        }

        public String getPs() {
            return ps;
        }

        public void setPs(String ps) {
            this.ps = ps;
        }

        public String getPno() {
            return pno;
        }

        public void setPno(String pno) {
            this.pno = pno;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "list=" + list +
                    ", totalPage='" + totalPage + '\'' +
                    ", ps='" + ps + '\'' +
                    ", pno='" + pno + '\'' +
                    '}';
        }
    }
}

package com.fitmesh.fitmeshbackend.adapter.out.payment.paywithaccount.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PWASendInvoiceRequest {

    @JsonProperty("request_ref")
    private String requestRef;

    @JsonProperty("request_type")
    private String requestType;

    private Auth auth;
    private Transaction transaction;

    public PWASendInvoiceRequest() {}

    // Getters and Setters
    public String getRequestRef() {
        return requestRef;
    }

    public void setRequestRef(String requestRef) {
        this.requestRef = requestRef;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // Nested Classes

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Auth {
        private String type;
        private String secure;

        @JsonProperty("auth_provider")
        private String authProvider;

        public Auth() {}

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSecure() {
            return secure;
        }

        public void setSecure(String secure) {
            this.secure = secure;
        }

        public String getAuthProvider() {
            return authProvider;
        }

        public void setAuthProvider(String authProvider) {
            this.authProvider = authProvider;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Transaction {
        @JsonProperty("mock_mode")
        private String mockMode;

        @JsonProperty("transaction_ref")
        private String transactionRef;

        @JsonProperty("transaction_desc")
        private String transactionDesc;

        @JsonProperty("transaction_ref_parent")
        private String transactionRefParent;

        private BigDecimal amount;
        private Customer customer;
        private Meta meta;
        private Map<String, Object> details;

        public Transaction() {
            this.details = new HashMap<>();
        }

        public String getMockMode() {
            return mockMode;
        }

        public void setMockMode(String mockMode) {
            this.mockMode = mockMode;
        }

        public String getTransactionRef() {
            return transactionRef;
        }

        public void setTransactionRef(String transactionRef) {
            this.transactionRef = transactionRef;
        }

        public String getTransactionDesc() {
            return transactionDesc;
        }

        public void setTransactionDesc(String transactionDesc) {
            this.transactionDesc = transactionDesc;
        }

        public String getTransactionRefParent() {
            return transactionRefParent;
        }

        public void setTransactionRefParent(String transactionRefParent) {
            this.transactionRefParent = transactionRefParent;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }

        public Meta getMeta() {
            return meta;
        }

        public void setMeta(Meta meta) {
            this.meta = meta;
        }

        public Map<String, Object> getDetails() {
            return details;
        }

        public void setDetails(Map<String, Object> details) {
            this.details = details;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Customer {
        @JsonProperty("customer_ref")
        private String customerRef;

        private String firstname;
        private String surname;
        private String email;

        @JsonProperty("mobile_no")
        private String mobileNo;

        public Customer() {}

        public String getCustomerRef() {
            return customerRef;
        }

        public void setCustomerRef(String customerRef) {
            this.customerRef = customerRef;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Meta {
        private String type;

        @JsonProperty("expires_in")
        private Integer expiresIn;

        @JsonProperty("skip_messaging")
        private Boolean skipMessaging;

        @JsonProperty("biller_code")
        private String billerCode;

        // Subscription-specific fields
        @JsonProperty("repeat_frequency")
        private String repeatFrequency;

        @JsonProperty("number_of_payments")
        private Integer number_of_payments;

        @JsonProperty("repeat_start_date")
        private String repeatStartDate;

        @JsonProperty("repeat_end_date")
        private String repeatEndDate;

        public Meta() {}

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(Integer expiresIn) {
            this.expiresIn = expiresIn;
        }

        public Boolean getSkipMessaging() {
            return skipMessaging;
        }

        public void setSkipMessaging(Boolean skipMessaging) {
            this.skipMessaging = skipMessaging;
        }

        public String getBillerCode() {
            return billerCode;
        }

        public void setBillerCode(String billerCode) {
            this.billerCode = billerCode;
        }

        public String getRepeatFrequency() {
            return repeatFrequency;
        }

        public void setRepeatFrequency(String repeatFrequency) {
            this.repeatFrequency = repeatFrequency;
        }

        public Integer getNumber_of_payments() {
            return number_of_payments;
        }

        public void setNumber_of_payments(Integer number_of_payments) {
            this.number_of_payments = number_of_payments;
        }

        public String getRepeatStartDate() {
            return repeatStartDate;
        }

        public void setRepeatStartDate(String repeatStartDate) {
            this.repeatStartDate = repeatStartDate;
        }

        public String getRepeatEndDate() {
            return repeatEndDate;
        }

        public void setRepeatEndDate(String repeatEndDate) {
            this.repeatEndDate = repeatEndDate;
        }
    }
}

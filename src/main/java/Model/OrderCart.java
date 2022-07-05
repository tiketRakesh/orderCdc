package Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order__cart")
public class OrderCart  implements Serializable {
    private static final long serialVersionUID = -8156661319723748186L;
    @Id
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "order_hash")
    private String orderHash;
    @Column(name = "order_timestamp")
    private Date orderTimestamp;
    @Column(name = "checkout_sequence")
    private Integer checkoutSequence;
    @Column(name = "payment_timestamp")
    private Date paymentTimestamp;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "total_customer_price")
    private BigDecimal totalCustomerPrice;
    @Column(name = "customer_currency")
    private String customerCurrency;
    @Column(name = "is_installment")
    private Byte isInstallment;
    @Column(name = "cc_installment")
    private Byte ccInstallment;
    @Column(name = "status_checkout")
    private String statusCheckout;
    @Column(name = "order_cs")
    private Long orderCs;
    @Column(name = "reseller_type")
    private String resellerType;
    @Column(name = "reseller_id")
    private Long resellerId;
    @Column(name = "api_key_id")
    private Long apiKeyId;
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "ip_user")
    private String ipUser;
    @Column(name = "ip_private")
    private String ipPrivate;
    @Column(name = "from_email")
    private String fromEmail;
    @Column(name = "cs_id")
    private Long csId;
    @Column(name = "media_type")
    private String mediaType;
    @Column(name = "user_lang")
    private String userLang;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
    public String getOrderHash() {
        return orderHash;
    }
    public void setOrderHash(String orderHash) {
        this.orderHash = orderHash;
    }
    public Date getOrderTimestamp() {
        return orderTimestamp;
    }
    public void setOrderTimestamp(Date orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
    public Integer getCheckoutSequence() {
        return checkoutSequence;
    }
    public void setCheckoutSequence(Integer checkoutSequence) {
        this.checkoutSequence = checkoutSequence;
    }
    public Date getPaymentTimestamp() {
        return paymentTimestamp;
    }
    public void setPaymentTimestamp(Date paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public BigDecimal getTotalCustomerPrice() {
        return totalCustomerPrice;
    }
    public void setTotalCustomerPrice(BigDecimal totalCustomerPrice) {
        this.totalCustomerPrice = totalCustomerPrice;
    }
    public String getCustomerCurrency() {
        return customerCurrency;
    }
    public void setCustomerCurrency(String customerCurrency) {
        this.customerCurrency = customerCurrency;
    }
    public Byte getIsInstallment() {
        return isInstallment;
    }
    public void setIsInstallment(Byte isInstallment) {
        this.isInstallment = isInstallment;
    }
    public Byte getCcInstallment() {
        return ccInstallment;
    }
    public void setCcInstallment(Byte ccInstallment) {
        this.ccInstallment = ccInstallment;
    }
    public String getStatusCheckout() {
        return statusCheckout;
    }
    public void setStatusCheckout(String statusCheckout) {
        this.statusCheckout = statusCheckout;
    }
    public Long getOrderCs() {
        return orderCs;
    }
    public void setOrderCs(Long orderCs) {
        this.orderCs = orderCs;
    }
    public String getResellerType() {
        return resellerType;
    }
    public void setResellerType(String resellerType) {
        this.resellerType = resellerType;
    }
    public Long getResellerId() {
        return resellerId;
    }
    public void setResellerId(Long resellerId) {
        this.resellerId = resellerId;
    }
    public Long getApiKeyId() {
        return apiKeyId;
    }
    public void setApiKeyId(Long apiKeyId) {
        this.apiKeyId = apiKeyId;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getIpUser() {
        return ipUser;
    }
    public void setIpUser(String ipUser) {
        this.ipUser = ipUser;
    }
    public String getIpPrivate() {
        return ipPrivate;
    }
    public void setIpPrivate(String ipPrivate) {
        this.ipPrivate = ipPrivate;
    }
    public String getFromEmail() {
        return fromEmail;
    }
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }
    public Long getCsId() {
        return csId;
    }
    public void setCsId(Long csId) {
        this.csId = csId;
    }
    public String getMediaType() {
        return mediaType;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    public String getUserLang() {
        return userLang;
    }
    public void setUserLang(String userLang) {
        this.userLang = userLang;
    }
    @Override
    public String toString() {
        return "OrderCart{" +
                "orderId=" + orderId +
                ", accountId=" + accountId +
                ", orderHash='" + orderHash + '\'' +
                ", orderTimestamp=" + orderTimestamp +
                ", checkoutSequence=" + checkoutSequence +
                ", paymentTimestamp=" + paymentTimestamp +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalCustomerPrice=" + totalCustomerPrice +
                ", customerCurrency='" + customerCurrency + '\'' +
                ", isInstallment=" + isInstallment +
                ", ccInstallment=" + ccInstallment +
                ", statusCheckout='" + statusCheckout + '\'' +
                ", orderCs=" + orderCs +
                ", resellerType='" + resellerType + '\'' +
                ", resellerId=" + resellerId +
                ", apiKeyId=" + apiKeyId +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", ipUser='" + ipUser + '\'' +
                ", ipPrivate='" + ipPrivate + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", csId=" + csId +
                ", mediaType='" + mediaType + '\'' +
                ", userLang='" + userLang + '\'' +
                '}';
    }
}


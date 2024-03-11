package mywebsite.finances.transactions;

import java.text.ParseException;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank(message = "rawString is required")
  private String rawString;

  @NotBlank(message = "accountNum is required")
  private String accountNum;

  private float amount;

  @NotBlank(message = "merchant is required")
  private String merchant;

  private String nickname;

  @NotNull(message = "category is required")
  private Category category;

  private String label;

  @NotNull(message = "transactionDate is required")
  private Date transactionDate;

  private Date postingDate;

  private String description;

  private String referenceNum;

  public Transaction() {

  }

  public Transaction(String rawString, String accountNum, float amount, String merchant,
      Category category, Date transactionDate, Date postingDate, String description,
      String referenceNum) throws ParseException {
    this.rawString = rawString;
    this.accountNum = accountNum;
    this.amount = amount;
    this.merchant = merchant;
    this.category = category;
    this.transactionDate = transactionDate;
    this.postingDate = postingDate;
    this.referenceNum = referenceNum;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRawString() {
    return rawString;
  }

  public void setRawString(String rawString) {
    this.rawString = rawString;
  }

  public String getAccountNum() {
    return accountNum;
  }

  public void setAccountNum(String accountNum) {
    this.accountNum = accountNum;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public String getMerchant() {
    return merchant;
  }

  public void setMerchant(String merchant) {
    this.merchant = merchant;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public Date getPostingDate() {
    return postingDate;
  }

  public void setPostingDate(Date postingDate) {
    this.postingDate = postingDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getReferenceNum() {
    return referenceNum;
  }

  public void setReferenceNum(String referenceNum) {
    this.referenceNum = referenceNum;
  }

  @Override
  public String toString() {
    return "Transaction [id=" + id + ", rawString=" + rawString + ", accountNum=" + accountNum
        + ", amount=" + amount + ", merchant=" + merchant + ", nickname=" + nickname + ", category="
        + category + ", label=" + label + ", transactionDate=" + transactionDate + ", postingDate="
        + postingDate + ", description=" + description + ", referenceNum=" + referenceNum + "]";
  }
}

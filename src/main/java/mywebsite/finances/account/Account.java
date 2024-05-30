package mywebsite.finances.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank(message = "accountNumber is required")
  private String accountNumber;

  @NotBlank(message = "institution is required")
  private String institution;

  @NotBlank(message = "name is required")
  private String name;

  // @ElementCollection
  // @CollectionTable(name = "account_value_history", joinColumns = {
  //     @JoinColumn(name = "account_id", referencedColumnName = "id") })
  // @MapKeyColumn(name = "snapshot_date")
  // @Column(name = "value")
  // private Map<Date, Double> accountValueHistory;

  public Account() {

  }

  public Account(String accountNumber, String institution, String name) {
    this.accountNumber = accountNumber;
    this.institution = institution;
    this.name = name;
    // accountValueHistory = new HashMap<Date, Double>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  // public Map<Date, Double> getAccountValueHistory() {
  //   return accountValueHistory;
  // }

  // public void setAccountValueHistory(Map<Date, Double> accountValueHistory) {
  //   this.accountValueHistory = accountValueHistory;
  // }

  // public Double addToAccountValueHistory(Date date, Double value) {
  //   return this.accountValueHistory.put(date, value);
  // }

  @Override
  public String toString() {
    return "Account [id=" + id + ", accountNumber=" + accountNumber + ", institution=" + institution
        + ", name=" + name + "]";
  }
}

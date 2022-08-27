package bo.visa.nincom.domain;

public class Bill {
    private Integer id;
    private String number;
    private String clientName;
    private String status;
    private String typeCurrency;
    private Double mount;
    private Boolean startflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeCurrency() {
        return typeCurrency;
    }

    public void setTypeCurrency(String typeCurrency) {
        this.typeCurrency = typeCurrency;
    }

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public Boolean getStartflag() {
        return startflag;
    }

    public void setStartflag(Boolean startflag) {
        this.startflag = startflag;
    }
}

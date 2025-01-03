package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bill_detail")
public class Bill_Detail_Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BILL_DETAIL")
    private Integer idBillDetail;

    @Column(name = "ID_BILL")
    private String idBill;

    @Column(name = "QUANTITY_SP")
    private Integer quantitySp;

    @Column(name = "TOTAL_DETAIL_PRICE")
    private Double totalDetailPrice;

    @Column(name = "UNIT_PRICE")
    private Double unitPrice;

    @Column(name = "ID_FINAL_PRODUCT", insertable = false, updatable = false)
    private Integer idFinalProduct;

    @Column(name = "AVAILABLE")
    private Boolean available;

    @Transient
    private String nameFinalProduct;

    @ManyToOne
    @JoinColumn(name = "ID_FINAL_PRODUCT", referencedColumnName = "ID_FINAL_PRODUCT", insertable = false, updatable = false)
    private Product_Final_Entity productFinalEntity;

    @ManyToOne
    @JoinColumn(name = "ID_BILL", referencedColumnName = "ID_BILL", insertable = false, updatable = false)
    private Bill_Entity billEntity;

    // Getters và Setters
    public Integer getIdBillDetail() {
        return idBillDetail;
    }

    public void setIdBillDetail(Integer idBillDetail) {
        this.idBillDetail = idBillDetail;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public Integer getQuantitySp() {
        return quantitySp;
    }

    public void setQuantitySp(Integer quantitySp) {
        this.quantitySp = quantitySp;
    }

    public Double getTotalDetailPrice() {
        return totalDetailPrice;
    }

    public void setTotalDetailPrice(Double totalDetailPrice) {
        this.totalDetailPrice = totalDetailPrice;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getIdFinalProduct() {
        return idFinalProduct;
    }

    public void setIdFinalProduct(Integer idFinalProduct) {
        this.idFinalProduct = idFinalProduct;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getNameFinalProduct() {
        return nameFinalProduct;
    }

    public void setNameFinalProduct(String nameFinalProduct) {
        this.nameFinalProduct = nameFinalProduct;
    }

    public Product_Final_Entity getProductFinalEntity() {
        return productFinalEntity;
    }

    public void setProductFinalEntity(Product_Final_Entity productFinalEntity) {
        this.productFinalEntity = productFinalEntity;
    }

    public Bill_Entity getBillEntity() {
        return billEntity;
    }

    public void setBillEntity(Bill_Entity billEntity) {
        this.billEntity = billEntity;
    }

    // Constructors
    public Bill_Detail_Entity(Integer idBillDetail, String idBill, Integer quantitySp, Double unitPrice, Integer idFinalProduct, Boolean available, String nameFinalProduct) {
        this.idBillDetail = idBillDetail;
        this.idBill = idBill;
        this.quantitySp = quantitySp;
        this.totalDetailPrice = unitPrice * quantitySp;
        this.unitPrice = unitPrice;
        this.idFinalProduct = idFinalProduct;
        this.nameFinalProduct = nameFinalProduct;
        this.available = available;
    }

    public Bill_Detail_Entity(Integer idBillDetail, String idBill, Integer quantitySp, Double unitPrice, Integer idFinalProduct, Boolean available) {
        this.idBillDetail = idBillDetail;
        this.idBill = idBill;
        this.quantitySp = quantitySp;
        this.unitPrice = unitPrice;
        this.idFinalProduct = idFinalProduct;
        this.available = available;
        this.totalDetailPrice = unitPrice * quantitySp;
    }

    public Bill_Detail_Entity() {
    }
}

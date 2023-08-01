package com.himedia.androidshoppingmall.Data;

public class CartBean {
    String goods_id;
    String goods_title;

    String goods_price;
    String imageRes;

    String goods_qty;

    String cart_id;

    public String getCart_id() { return cart_id; }

    public void setCart_id(String cart_id) { this.cart_id = cart_id; }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getImageRes() {
        return imageRes;
    }

    public void setImageRes(String imageRes) {
        this.imageRes = imageRes;
    }

    public String getGoods_qty() {
        return goods_qty;
    }

    public void setGoods_qty(String goods_qty) {
        this.goods_qty = goods_qty;
    }

    public CartBean() {
    }

    public CartBean(String goods_id, String goods_title, String goods_price, String imageRes) {
        this.goods_id = goods_id;
        this.goods_title = goods_title;
        this.goods_price = goods_price;
        this.imageRes = imageRes;
    }

    // 장바구니용 qoods_qty 에 cart_goods_qty가 들어간다. 20230727 by Dean
    // 생성자 오버로드( 메서드, 리턴타입은 같으나 인수의 타입이나 갯수가 다른 경우 )
    public CartBean(String goods_id, String goods_title, String goods_price, String imageRes, String goods_qty) {
        this.goods_id = goods_id;
        this.goods_title = goods_title;
        this.goods_price = goods_price;
        this.imageRes = imageRes;
        this.goods_qty = goods_qty;
    }

    public CartBean(String goods_id, String goods_title, String goods_price, String imageRes, String goods_qty, String cart_id) {
        this.goods_id = goods_id;
        this.goods_title = goods_title;
        this.goods_price = goods_price;
        this.imageRes = imageRes;
        this.goods_qty = goods_qty;
        this.cart_id = cart_id;
    }
}

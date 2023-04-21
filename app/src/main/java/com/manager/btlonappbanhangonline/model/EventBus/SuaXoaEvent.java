package com.manager.btlonappbanhangonline.model.EventBus;

import com.manager.btlonappbanhangonline.model.NewProduct;

public class SuaXoaEvent {
    NewProduct sanPhamMoi;

    public SuaXoaEvent(NewProduct sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }

    public NewProduct getSanPhamMoi() {
        return sanPhamMoi;
    }

    public void setSanPhamMoi(NewProduct sanPhamMoi) {
        this.sanPhamMoi = sanPhamMoi;
    }
}

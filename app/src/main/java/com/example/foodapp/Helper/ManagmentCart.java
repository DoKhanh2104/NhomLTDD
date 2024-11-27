package com.example.foodapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.foodapp.Domain.Foods;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    // Thêm món ăn vào giỏ hàng
    public void insertFood(Foods item) {
        ArrayList<Foods> listCart = getListCart();
        boolean existAlready = false;
        int index = 0;

        // Kiểm tra xem món đã tồn tại trong giỏ hàng hay chưa
        for (int i = 0; i < listCart.size(); i++) {
            if (listCart.get(i).getId() == item.getId()) {
                existAlready = true;
                index = i;
                break;
            }
        }

        if (existAlready) {
            listCart.get(index).setNumberInCart(listCart.get(index).getNumberInCart() + item.getNumberInCart());
        } else {
            listCart.add(item);
        }

        tinyDB.putListObject("CartList", listCart);
        Toast.makeText(context, "Đã thêm vào Giỏ Hàng", Toast.LENGTH_SHORT).show();
    }

    // Lấy danh sách món ăn trong giỏ hàng
    public ArrayList<Foods> getListCart() {
        ArrayList<Foods> objectList = tinyDB.getListObject("CartList");
        ArrayList<Foods> foodsList = new ArrayList<>();

        for (Object obj : objectList) {
            if (obj instanceof Foods) {
                foodsList.add((Foods) obj);
            }
        }
        return foodsList; // Trả về danh sách Foods
    }


    // Tính tổng phí trong giỏ hàng
    public double getTotalFee() {
        ArrayList<Foods> listCart = getListCart();
        double totalFee = 0;

        for (Foods food : listCart) {
            totalFee += food.getPrice() * food.getNumberInCart();
        }

        return totalFee;
    }

    // Giảm số lượng món ăn
    public void minusNumberItem(ArrayList<Foods> listCart, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listCart.get(position).getNumberInCart() == 1) {
            listCart.remove(position);
        } else {
            listCart.get(position).setNumberInCart(listCart.get(position).getNumberInCart() - 1);
        }

        tinyDB.putListObject("CartList", listCart);
        changeNumberItemsListener.change();
    }

    // Tăng số lượng món ăn
    public void plusNumberItem(ArrayList<Foods> listCart, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listCart.get(position).setNumberInCart(listCart.get(position).getNumberInCart() + 1);

        tinyDB.putListObject("CartList", listCart);
        changeNumberItemsListener.change();
    }

    // Xóa toàn bộ giỏ hàng
    public void clear() {
        tinyDB.putListObject("CartList", new ArrayList<Foods>());
    }

}

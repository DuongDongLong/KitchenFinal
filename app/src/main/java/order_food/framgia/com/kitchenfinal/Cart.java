package order_food.framgia.com.kitchenfinal;

public class Cart {
    private String name;
    private String description;
    private String image;
    private String discount;
    private String menuId;
    private String price;
    private int quantity;

    public Cart(String name, String description, String image, String discount, String menuId, String price, int quantity) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.discount = discount;
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

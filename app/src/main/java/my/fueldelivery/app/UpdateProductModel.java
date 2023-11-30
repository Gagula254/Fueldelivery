package my.fueldelivery.app;

public class UpdateProductModel {

    String Products,RandomUID,Description,Quantity,Price,ImageURL,AttendantId;

    // Press Alt+insert

    public UpdateProductModel(){

    }

    public String getProducts() {
        return Products;
    }

    public void setProducts(String products) {
        Products = products;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

//    public String getImageURL() {
//        return ImageURL;
//    }

//    public void setImageURL(String imageURL) {
//        ImageURL = imageURL;
//    }

    public String getAttendantId() {
        return AttendantId;
    }

    public void setAttendantId(String attendantId) {
        AttendantId = attendantId;
    }
}

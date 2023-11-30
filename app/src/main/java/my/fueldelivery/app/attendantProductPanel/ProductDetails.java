package my.fueldelivery.app.attendantProductPanel;

public class ProductDetails {

    public String Products,Quantity,Price,Description,RandomUID,Attendantid;
    // Alt+insert

    public ProductDetails(String products, String quantity, String price, String description,  String randomUID, String attendantid) {
        Products = products;
        Quantity = quantity;
        Price = price;
        Description = description;
//        ImageURL = imageURL;
        RandomUID = randomUID;
        Attendantid = attendantid;
    }
}

export default function parsingPrice(price) {
    var formattedPrice;
    if (price != null) {
      formattedPrice = price.toLocaleString();
    } else {
      formattedPrice = "N/A";
    }
    return formattedPrice;
  }
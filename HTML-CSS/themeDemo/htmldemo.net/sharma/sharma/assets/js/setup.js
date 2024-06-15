<script>
document.addEventListener("DOMContentLoaded", function() {

      var urlsToRemove = [
        "shop-4-column.html",
        "shop-right-sidebar.html",
        "index-2.html",
        "index-3.html",
        "index-4.html",
        "shop-list-right-sidebar.html",
        "single-product.html",
        "single-product-affiliate.html",
        "single-product-group.html",
        "single-product-tabstyle-2.html",
        "single-product-tabstyle-3.html",
        "single-product-slider.html",
        "single-product-gallery-left.html",
        "single-product-gallery-right.html",
        "single-product-sticky-left.html",
        "single-product-sticky-right.html"
      ];

    
    var tagsToSearch = ["li", "div", "span", "p"];
    
    // Duyệt qua từng loại thẻ
    tagsToSearch.forEach(function(tag) {
      urlsToRemove.forEach(function(url) {
        var elementsToRemove = document.querySelectorAll(tag + " > a[href='" + url + "']");
        elementsToRemove.forEach(function(element) {
          element.parentNode.parentNode.removeChild(element.parentNode);
        })
      })
    });
});
</script>

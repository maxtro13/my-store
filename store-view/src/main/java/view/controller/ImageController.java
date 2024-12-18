//package view.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import view.client.StoreRestClient;
//import view.entity.Image;
//
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("/store/images")
//public class ImageController {
//
//    private final StoreRestClient storeRestClient;
//
//    @ModelAttribute("image")
//    public Image image(@PathVariable("imageId") Long imageId) {
//        return this.storeRestClient.getImageById(imageId);
//    }
//
//    @GetMapping("/{imageId}")
//    public String getImage(@PathVariable(name = "imageId") Long imageId, Model model) {
//        model.addAttribute("image", this.storeRestClient.getImageById(imageId));
//        return "store/dishes/dish";
//    }
//}
//todo разобраться почему получается ошибка при создании 
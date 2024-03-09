package propensi.c06.sipp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import propensi.c06.sipp.dto.request.CreateRencanaRequestDTO;
import propensi.c06.sipp.dto.response.LoginJwtResponseDTO;
import propensi.c06.sipp.model.Rencana;
import propensi.c06.sipp.service.BarangService;
import propensi.c06.sipp.service.RencanaService;
import propensi.c06.sipp.service.UserService;
import propensi.c06.sipp.service.VendorService;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/rencana")
public class RencanaController {
    @Autowired
    private RencanaService rencanaService;

    @Autowired
    private BarangService barangService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String daftarRencana(Model model) {
        // HttpSession session = request.getSession(false);
        // String jwtToken = (String) session.getAttribute("token");
        // LoginJwtResponseDTO loginJwtResponseDTO = userService.retrieveUser(jwtToken);
        HttpServletRequest request
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        System.out.println(role);
        Claims claims = Jwts.parser()
                .setSigningKey("yourSecretKey")
                .parseClaimsJws(role)
                .getBody();
        String roleName = (String) claims.get("role");
        System.out.println(roleName);
        // if () {
        //     return "view-daftar-rencana-keuangan";
        // } else {
        //     return "view-daftar-rencana";
        // }
        return "view-daftar-rencana";
    }

    @GetMapping(value = "/{id}")
    public String detailRencana(@PathVariable(value = "id") Long id, Model model) {
        Rencana rencana = rencanaService.getRencanaById(id);
        model.addAttribute("rencana", rencana);

        // if () {
        //     return "view-detail-rencana-manajer";
        // } else if () {
        //     return "view-detail-rencana-keuangan";
        // } else {
        //     return "view-detail-rencana-operasional";
        // }
        return null;
    }

    @GetMapping("/create")
    public String formAddRencana(Model model) {
        model.addAttribute("rencanaDTO", new CreateRencanaRequestDTO());
        model.addAttribute("listVendorExisted", vendorService.getAllVendor());
        model.addAttribute("listBarangExisted", barangService.getAllBarang());
        return "form-create-rencana";
    }

    @PostMapping(value = "/create", params = {"addRow"})
    public String addRowRencana(@ModelAttribute CreateRencanaRequestDTO rencanaDTO, Model model) {
        if (rencanaDTO.getListBarangRencana() == null || rencanaDTO.getListBarangRencana().size() == 0) {
            rencanaDTO.setListBarangRencana(new ArrayList<>());        
        }
        rencanaDTO.getListBarangRencana().add(new CreateRencanaRequestDTO.BarangRencanaDTO());
        model.addAttribute("rencanaDTO", rencanaDTO);
        model.addAttribute("listVendorExisted", vendorService.getAllVendor());
        model.addAttribute("listBarangExisted", barangService.getAllBarang());
        return "form-create-rencana";
    }

    @PostMapping(value = "/create", params = {"deleteRow"})
    public String deleteRowRencana(
        @ModelAttribute CreateRencanaRequestDTO rencanaDTO, @RequestParam("deleteRow") int row, Model model) {
        rencanaDTO.getListBarangRencana().remove(row);
        model.addAttribute("rencanaDTO", rencanaDTO);
        model.addAttribute("listVendorExisted", vendorService.getAllVendor());
        model.addAttribute("listBarangExisted", barangService.getAllBarang());
        return "form-create-rencana";
    }

    @PostMapping("/create")
    public String addRencana(
        @Valid @ModelAttribute CreateRencanaRequestDTO rencanaDTO, 
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : errors) {
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }
            model.addAttribute("errorMessage", errorMessage.toString());
            return "error-view";
        }
        Rencana rencana = rencanaService.saveRencana(rencanaDTO);
        model.addAttribute("id", rencana.getIdRencana());
        model.addAttribute("nama", rencana.getNamaRencana());
        return "view-daftar-rencana";
    }
}

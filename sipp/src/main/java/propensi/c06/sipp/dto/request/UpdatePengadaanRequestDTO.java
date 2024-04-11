package propensi.c06.sipp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import propensi.c06.sipp.dto.PengadaanRequestDTO;
import propensi.c06.sipp.model.Barang;
import propensi.c06.sipp.model.Vendor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePengadaanRequestDTO {
    private String idPengadaan;
    private String namaPengadaan;
    private String tanggalPengadaan;
    private Vendor vendor;
    private int diskonKeseluruhan;
    private List<PengadaanBarangDTO> listBarang;

    @Data
    public static class PengadaanBarangDTO {
        private Long idPengadaanBarang;
        private int jumlahBarang;
        private int hargaBarang;
        private int diskonSatuan;
        private Barang barang;
    }
}

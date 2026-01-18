import java.util.Scanner;

public class atm { // Dosya adi atm.java oldugu icin sinif adi da atm

    // --- GLOBAL DEĞİŞKENLER ---
    static double bakiye = 5000.0;
    static String tanimliPin = "1923";

    // --- METOTLAR (Methods) ---

    // Şık bir karşılama ekranı
    public static void karsilamaEkrani() {
        System.out.println("\n\t*****************************************");
        System.out.println("\t* GELİSMİS YAPI BANK        *");
        System.out.println("\t* BANKAMIZA HOSGELDİNİZ    *");
        System.out.println("\t*****************************************");
    }

    // Ana işlem menüsünü basan metot
    public static void anaMenuyuGoster() {
        System.out.println("\n---------------- MENU ----------------");
        System.out.println("1 - Bakiye Sorgulama");
        System.out.println("2 - Para Cekme (Nakit)");
        System.out.println("3 - Para Yatirma (Hazne)");
        System.out.println("4 - Fatura Odeme (Kurumsal)");
        System.out.println("5 - TL Yukleme (Mobil)");
        System.out.println("6 - Guvenlik: Sifre Degistir");
        System.out.println("7 - IBAN Transfer (EFT/Havale)");
        System.out.println("8 - Sistemden Guvenli Cikis");
        System.out.println("--------------------------------------");
        System.out.print("Seciminiz [1-8]: ");
    }

    // Bakiye yeterli mi kontrol eden metot
    public static boolean limitKontrol(double miktar) {
        if (miktar <= 0) {
            System.out.println("HATA: Gecersiz tutar girdiniz!");
            return false;
        }
        if (miktar > bakiye) {
            System.out.println("HATA: Yetersiz bakiye! Mevcut: " + bakiye + " TL");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // --- DIZILER (Arrays) ---
        // Transfer yapilacak bankalar listesi
        String[] bankaListesi = {"Garanti BBVA", "Yapi Kredi", "Ziraat Bankasi", "Halkbank", "QNB Finansbank", "Is Bankasi"};
        
        // Islem gecmisi logu (Son 5 islem)
        String[] gecmisIslemler = new String[5];
        int islemSayaci = 0;

        karsilamaEkrani();

        // --- GIRIS BOLUMU (Loop & Condition) ---
        int hak = 3;
        boolean login = false;

        while (hak > 0) {
            System.out.print("\nGuvenlik Protokolu: 4 Haneli PIN: ");
            String girilen = scanner.next();

            if (girilen.equals(tanimliPin)) {
                System.out.println(">> Dogrulama Basarili. Sisteme erisiliyor...");
                login = true;
                break;
            } else {
                hak--;
                System.out.println(">> HATALI PIN! Kalan deneme hakki: " + hak);
            }
        }

        if (!login) {
            System.err.println("\n[BLOKE] Ust uste hatali giris. Kartiniza el konuldu!");
            return;
        }

        // --- ANA ISLEM DONGUSU (Switch-Case & Loops) ---
        while (true) {
            anaMenuyuGoster();
            int secim = scanner.nextInt();

            switch (secim) {
                case 1:
                    System.out.println("\n[BAKIYE SORGULAMA]");
                    System.out.println("Hesabinizdaki toplam tutar: " + bakiye + " TL");
                    gecmisIslemler[islemSayaci % 5] = "Bakiye Sorgulama yapildi.";
                    islemSayaci++;
                    break;

                case 2:
                    System.out.println("\n[PARA CEKME]");
                    System.out.print("Cekmek istediginiz tutari girin: ");
                    double cekilecek = scanner.nextDouble();
                    if (limitKontrol(cekilecek)) {
                        bakiye -= cekilecek;
                        System.out.println("Islem tamamlandi. Paranizi hazneden alin.");
                        gecmisIslemler[islemSayaci % 5] = cekilecek + " TL para cekildi.";
                        islemSayaci++;
                    }
                    break;

                case 3:
                    System.out.println("\n[PARA YATIRMA]");
                    System.out.print("Yatirilacak tutari hazneye yerlestirin (Tutari girin): ");
                    double yatirilan = scanner.nextDouble();
                    if (yatirilan > 0) {
                        bakiye += yatirilan;
                        System.out.println("Yeni bakiyeniz: " + bakiye + " TL");
                        gecmisIslemler[islemSayaci % 5] = yatirilan + " TL para yatirildi.";
                        islemSayaci++;
                    }
                    break;

                case 4:
                    System.out.println("\n[FATURA ODEME]");
                    System.out.println("1-Elektrik\n2-Su\n3-Dogalgaz");
                    int faturaTip = scanner.nextInt();
                    System.out.print("Fatura tutari: ");
                    double faturaTutar = scanner.nextDouble();
                    if (limitKontrol(faturaTutar)) {
                        bakiye -= faturaTutar;
                        System.out.println("Faturaniz odendi. Kalan: " + bakiye);
                        gecmisIslemler[islemSayaci % 5] = "Fatura odemesi yapildi.";
                        islemSayaci++;
                    }
                    break;

                case 5: // Mobil TL Yukleme - YENI EKLENDI
                    System.out.println("\n[MOBIL TL YUKLEME]");
                    System.out.print("Yuklenecek Telefon No (5XX...): ");
                    String telNo = scanner.next();
                    System.out.print("Yuklenecek Tutar: ");
                    double tlTutar = scanner.nextDouble();
                    
                    if (limitKontrol(tlTutar)) {
                        bakiye -= tlTutar;
                        System.out.println(telNo + " nolu hatta " + tlTutar + " TL yuklendi.");
                        gecmisIslemler[islemSayaci % 5] = "Mobil TL yuklemesi yapildi.";
                        islemSayaci++;
                    }
                    break;

                case 6: // Sifre Degistirme - YENI EKLENDI
                    System.out.println("\n[SIFRE DEGISTIRME]");
                    System.out.print("Mevcut PIN'inizi girin: ");
                    String mevcutPin = scanner.next();
                    
                    if (mevcutPin.equals(tanimliPin)) {
                        System.out.print("Yeni PIN giriniz: ");
                        tanimliPin = scanner.next(); 
                        System.out.println("Sifreniz basariyla degistirildi.");
                        gecmisIslemler[islemSayaci % 5] = "PIN kodu degistirildi.";
                        islemSayaci++;
                    } else {
                        System.out.println("HATA: Mevcut PIN yanlis!");
                    }
                    break;

                case 7:
                    System.out.println("\n[IBAN TRANSFER]");
                    // DONGU VE DIZI KULLANIMI (Banka listeleme)
                    System.out.println("Sistemde kayitli bankalar:");
                    for (int i = 0; i < bankaListesi.length; i++) {
                        System.out.println((i + 1) + ". " + bankaListesi[i]);
                    }
                    System.out.print("Banka secin: ");
                    int bSecim = scanner.nextInt();
                    
                    System.out.print("11 Haneli IBAN girin: ");
                    String iban = scanner.next();
                    
                    System.out.print("Transfer tutari: ");
                    double transferTutar = scanner.nextDouble();
                    
                    // Islem ucreti: 5 TL
                    if (limitKontrol(transferTutar + 5)) {
                        bakiye -= (transferTutar + 5);
                        System.out.println(bankaListesi[bSecim-1] + " hesabina transfer basarili.");
                        gecmisIslemler[islemSayaci % 5] = transferTutar + " TL EFT yapildi.";
                        islemSayaci++;
                    }
                    break;

                case 8:
                    System.out.println("\nGuvenli cikis yapiliyor. Kartinizi almayi unutmayin!");
                    System.out.println("Bizi tercih ettiginiz icin tesekkurler Gakgos!");
                    return;

                default:
                    System.out.println("Gecersiz islem kodu! Lutfen 1-8 arasi secin.");
            }
        }
    }
}

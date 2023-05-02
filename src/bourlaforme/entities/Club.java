package bourlaforme.entities;

public class Club {
    private int id;
    private String nom;
    private String localisation;
    private String image;
    private String typeActivite;
    private int idClubOwnerId;
    private String telephone;
    private String description;
    private String prix;
    private double longitude;
    private double latitude;

    public Club(int id, String nom, String localisation, String image, String typeActivite, int idClubOwnerId, String telephone, String description, String prix, double longitude, double latitude) {
        this.id = id;
        this.nom = nom;
        this.localisation = localisation;
        this.image = image;
        this.typeActivite = typeActivite;
        this.idClubOwnerId = idClubOwnerId;
        this.telephone = telephone;
        this.description = description;
        this.prix = prix;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Club(String nom, String localisation, String image, String typeActivite, int idClubOwnerId, String telephone, String description, String prix, double longitude, double latitude) {
        this.nom = nom;
        this.localisation = localisation;
        this.image = image;
        this.typeActivite = typeActivite;
        this.idClubOwnerId = idClubOwnerId;
        this.telephone = telephone;
        this.description = description;
        this.prix = prix;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public int getIdClubOwnerId() {
        return idClubOwnerId;
    }

    public void setIdClubOwnerId(int idClubOwnerId) {
        this.idClubOwnerId = idClubOwnerId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

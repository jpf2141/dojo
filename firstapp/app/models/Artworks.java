package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Artworks extends Model {

    @Id
    public Long artid;


    public Long uid;
    public String filePath;
    public String title;
    public int votes;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "upvotes")
    public List<Users> users;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    public Users user;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aucId")
    public Auctions auction;

    public static Finder<Long,Artworks> find = new Finder<Long,Artworks>(Long.class, Artworks.class);

        public static Artworks addArtwork(String filename, String email ) {
            Artworks artwork = new Artworks();
            String path = "https://s3.amazonaws.com/dojoart/";
            path += filename;
            artwork.filePath = path;
            Users user = Users.findByEmail(email);
            artwork.uid = user.uid;
            artwork.votes = 0;
            artwork.save();
            return artwork;
    }
}

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
    @JoinColumn(name = "user")
    public Users user;




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aucId")
    public Auctions auction;

    public static Finder<Long,Artworks> find = new Finder<Long,Artworks>(Long.class, Artworks.class);

        public static Artworks addArtwork(String filename, String email, String title) {
            Artworks artwork = new Artworks();
            String path = "https://s3.amazonaws.com/dojoart/art/";
            path += filename;
            artwork.filePath = path;
            artwork.title=title;
            Users user = Users.findByEmail(email);
            artwork.user = user;
            artwork.uid = user.uid;
            artwork.votes = 0;
            Auctions auc=new Auctions();
            auc.openDate="December 17, 2015";
            auc.closeDate="December 30, 2015";
            auc.bidCount=0L;
            auc.currentBid=0L;
            auc.ended=0;
            auc.userWithHighBid=user;
            auc.artwork=artwork;
            auc.save();
            artwork.auction=auc;
            artwork.save();
            
            return artwork;
    }
}

package dyndoc.firebase;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import org.slf4j.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by vjayanthi on 5/10/17.
 */
public class LoginDataAccess {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(LoginDataAccess.class);

    private static final String DATABASE_URL = "https://testfirebase1-76feb.firebaseio.com/";
    private static DatabaseReference database;

    private static final Map<String, String> map = new HashMap<>();


    public static void Initialize() throws Exception {
        if(database != null)
            return;

        ClassLoader classLoader = LoginDataAccess.class.getClassLoader();
        FileInputStream serviceAccount = new FileInputStream(classLoader.getResource("service-account.json").getPath());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl(DATABASE_URL)
                .build();

        FirebaseApp.initializeApp(options);

        database = FirebaseDatabase.getInstance().getReference();
        Semaphore semaphore = new Semaphore(0);


        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildName) {
                LOG.info("Adding [" + dataSnapshot.getKey() + ", " + dataSnapshot.getValue().toString() + "]");

                map.put(dataSnapshot.getKey(),  dataSnapshot.getValue().toString());
                semaphore.release();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                LOG.info("Updating " + dataSnapshot.getKey() + " with " + dataSnapshot.getValue().toString());

                map.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                LOG.info("Removing " + dataSnapshot.getKey());

                map.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        semaphore.acquire();
        LOG.info("Started sync of data.");
    }


    public Object getData() throws Exception {
        return map;
    }
}

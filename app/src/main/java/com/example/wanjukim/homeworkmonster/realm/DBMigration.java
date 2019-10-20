package com.example.wanjukim.homeworkmonster.realm;

import com.example.wanjukim.homeworkmonster.models.Image;
import com.example.wanjukim.homeworkmonster.models.Semester;
import com.example.wanjukim.homeworkmonster.models.Subject;
import com.example.wanjukim.homeworkmonster.models.WorkItem;

import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;

/**
 * Created by Wanju Kim on 2018-02-11.
 */

public class DBMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema objectSchema = schema.get(WorkItem.class.getSimpleName());
            migrateIDField(objectSchema);
            objectSchema = schema.get(Semester.class.getSimpleName());
            migrateIDField(objectSchema);
            objectSchema = schema.get(Subject.class.getSimpleName());
            migrateIDField(objectSchema);

            oldVersion++;
        }

        if (oldVersion == 1) {
            RealmObjectSchema objectSchema = schema.get(WorkItem.class.getSimpleName());
            objectSchema.addPrimaryKey("id");
            objectSchema = schema.get(Semester.class.getSimpleName());
            objectSchema.addPrimaryKey("id");
            objectSchema = schema.get(Subject.class.getSimpleName());
            objectSchema.addPrimaryKey("id");

            oldVersion++;
        }

        if (oldVersion == 2) {
            RealmObjectSchema objectSchema = schema.create(Image.class.getSimpleName()).addField(
                    "id", int.class)
                    .addField("bucketId", int.class).addField("path", String.class).addPrimaryKey("id");
            schema.get(WorkItem.class.getSimpleName()).addRealmObjectField("image", objectSchema);

            oldVersion++;
        }

        if (oldVersion == 3) {
            RealmObjectSchema objectSchema = schema.get(Image.class.getSimpleName());
            migrateIDField(objectSchema);

            oldVersion++;
        }

        if (oldVersion == 4) {
            RealmObjectSchema objectSchema = schema.get(Image.class.getSimpleName());
            objectSchema.addPrimaryKey("id");

            oldVersion++;
        }

        if (oldVersion == 5) {
            RealmObjectSchema objectSchema = schema.get(Semester.class.getSimpleName());
            objectSchema.addField("defaultFlag", boolean.class);

            oldVersion++;
        }

        if (oldVersion == 6) {
            RealmObjectSchema objectSchema = schema.get(Semester.class.getSimpleName());
            objectSchema.addField("semester", String.class);

            oldVersion++;
        }

        if (oldVersion == 7) {
            RealmObjectSchema objectSchema = schema.get(Subject.class.getSimpleName());
            objectSchema.addField("professor", String.class)
                    .addField("classroom", String.class).addField("memo", String.class);

            oldVersion++;
        }
    }

    private void migrateIDField(RealmObjectSchema objectSchema) {
        objectSchema.addField("newId", String.class);
        objectSchema.transform(new RealmObjectSchema.Function() {
            @Override
            public void apply(DynamicRealmObject obj) {
                obj.setString("newId", String.valueOf(obj.getInt("id")));
            }
        });
        objectSchema.removeField("id");
        objectSchema.renameField("newId", "id");
    }
}

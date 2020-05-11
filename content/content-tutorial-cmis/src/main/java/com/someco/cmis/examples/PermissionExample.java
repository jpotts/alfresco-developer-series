package com.someco.cmis.examples;

import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.runtime.OperationContextImpl;
import org.apache.chemistry.opencmis.commons.data.Ace;
import org.apache.chemistry.opencmis.commons.data.Acl;

import java.util.List;

/**
 * Created by jpotts, Metaversant on 10/5/18.
 */
public class PermissionExample extends CMISExampleBase {
    public static void main(String[] args) {
        PermissionExample pe = new PermissionExample();
        pe.setUser("admin");
        pe.setPassword("admin");
        pe.doExample();
        System.exit(0);
    }

    public void doExample() {
        OperationContext oc = new OperationContextImpl();
        oc.setIncludeAcls(true);
        Folder folder = (Folder) getSession().getObject("workspace://SpacesStore/5c8251c3-d309-4c88-a397-c408f4b34ed3", oc);

        // grab the ACL
        Acl acl = folder.getAcl();

        // dump the entries to sysout
        dumpAcl(acl);

        // iterate over the ACL Entries, removing the one that matches the id we want to remove
        List<Ace> aces = acl.getAces();
        for (Ace ace : aces) {
            if (ace.getPrincipalId().equals("tuser2")) {
                aces.remove(ace);
            }
        }

        // update the object ACL with the new list of ACL Entries
        folder.setAcl(aces);

        // refresh the object
        folder.refresh();

        // dump the acl to show the update
        acl = folder.getAcl();
        dumpAcl(acl);
    }

    public void dumpAcl(Acl acl) {
        List<Ace> aces = acl.getAces();
        for (Ace ace : aces) {
            System.out.println(String.format("%s has %s access", ace.getPrincipalId(), ace.getPermissions()));
        }
    }
}

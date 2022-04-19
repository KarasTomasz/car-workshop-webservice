package pl.tkaras.carworkshopwebservice.model.entity;

import javax.persistence.*;

@Entity
public class AppUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role = "CLIENT";
    private boolean isAccountNonExpired = false;
    private boolean isAccountNonLocked = false;
    private boolean isCredentialsNonExpired = false;
    private boolean isEnabled = false;

    @OneToOne
    @JoinColumn(name = "appUserId")
    AppUser appUser;

    public AppUserDetails() {
    }

    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String role = "CLIENT";
        private boolean isAccountNonExpired = false;
        private boolean isAccountNonLocked = false;
        private boolean isCredentialsNonExpired = false;
        private boolean isEnabled = false;
        AppUser appUser;

        public Builder role(String role){
            this.role = role;
            return this;
        }

        public Builder isAccountNonExpired(boolean isAccountNonExpired){
            this.isAccountNonExpired = isAccountNonExpired;
            return this;
        }

        public Builder isAccountNonLocked(boolean isAccountNonLocked){
            this.isAccountNonLocked = isAccountNonLocked;
            return this;
        }

        public Builder isCredentialsNonExpired(boolean isCredentialsNonExpired){
            this.isCredentialsNonExpired = isCredentialsNonExpired;
            return this;
        }

        public Builder isEnabled(boolean isEnabled){
            this.isEnabled = isEnabled;
            return this;
        }

        public Builder appUser(AppUser appUser){
            this.appUser = appUser;
            return this;
        }

        public AppUserDetails build(){
            AppUserDetails userSecurityDetails = new AppUserDetails();
            userSecurityDetails.setAccountNonLocked(isAccountNonLocked);
            userSecurityDetails.setAccountNonExpired(isAccountNonExpired);
            userSecurityDetails.setCredentialsNonExpired(isCredentialsNonExpired);
            userSecurityDetails.setEnabled(isEnabled);
            userSecurityDetails.setAppUser(appUser);
            return userSecurityDetails;
        }
    }
}

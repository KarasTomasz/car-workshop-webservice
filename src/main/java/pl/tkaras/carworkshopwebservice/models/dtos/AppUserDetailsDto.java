package pl.tkaras.carworkshopwebservice.models.dtos;

public class AppUserDetailsDto {

    private String username;
    private String role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String username;
        private String role;
        private boolean isAccountNonExpired;
        private boolean isAccountNonLocked;
        private boolean isCredentialsNonExpired;
        private boolean isEnabled;

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder userRole(String role){
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
            this.isEnabled = isAccountNonExpired;
            return this;
        }

        public AppUserDetailsDto build(){
            AppUserDetailsDto appUserDetailsDto = new AppUserDetailsDto();
            appUserDetailsDto.username = username;
            appUserDetailsDto.role = role;
            appUserDetailsDto.isAccountNonExpired = isAccountNonExpired;
            appUserDetailsDto.isAccountNonLocked = isAccountNonLocked;
            appUserDetailsDto.isCredentialsNonExpired = isCredentialsNonExpired;
            appUserDetailsDto.isEnabled = isEnabled;
            return appUserDetailsDto;
        }

    }
}

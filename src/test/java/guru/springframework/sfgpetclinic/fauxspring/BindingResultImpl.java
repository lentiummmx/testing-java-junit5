package guru.springframework.sfgpetclinic.fauxspring;

public class BindingResultImpl implements BindingResult {
    @Override
    public void rejectValue(String lastName, String notFound, String not_found) {
        //TODO (20210503)
    }

    @Override
    public boolean hasErrors() {
        return false;
    }
}

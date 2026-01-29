void switch_test() {
    if(true) {
        for(int i = 0; i < 5; ++i) {
            int a = 0;
            switch(i) {
                case 1:
                    break;
                case 2:
                case 3:
                case 4:
                    a = 3;
                    break;
                default:
                    return;
            }
        }
    }
}

void goto_test() {
    int boba = 1;
    if(boba > 3) {
        goto: label;
    } else {
        return;
    }
label:
    for(int i = 0; i < 10; ++i) {
        int b = i;
    }
}


void elseif_test() {
    int a = 0;
    if(a > 0) {
        int b = 42;
    } else if(a == 0) {
        int b = 52;
    } else if(a < 0) {
        int b = 37;
    } else {
        if (true) {
            int b = 3;
        } else {
            int c = 3;
            return;
        }
    }

    int b = 0;
}

void dowhile_test() {
    int j = 10;
    int i = 10;
    if(false) {
        while(--i) {
            int st1 = i;
        }
    } else {
        do {
            int st2 = j;
        } while(--i);
    }
}

void statement_test() {
    int a = 10;
    int b = 11;
}

void return_test() {
    int a = 10;

    for(int i = 0; i < 5; ++i) {
        for(int j = 0; j < 5; ++j) {
            return;
        }
    }
}

void break_test() {
    int a = 10;

    for(int i = 0; i < 5; ++i) {
        for(int j = 0; j < 5; ++j) {
            if(i < j) {
                break;
            } else {
                int c = 0;
            }

            if(j < 3) {
                continue;
            } else {
                int g = 0;
            }
        }
    }
}

void continue_test() {
    int a = 10;

    for(int i = 0; i < 5; ++i) {
        for(int j = 0; j < 5; ++j) {
            continue;
        }
    }
}


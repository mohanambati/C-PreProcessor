                                               




int printf(const char *s, ...);
int strcmp(const char *s, const char *d);

  
  
 
  
 
         


 
  
     
  



static const char *strdown( char *foo)
{
    static char temp[ 50 ];
    char *scan = temp;
    *scan++ = *foo++; 
    while ( *foo )
        if ( *foo >= 'A' && *foo <= 'Z' )
            *scan++ = *foo++ + 0x20;
        else
            *scan++ = *foo++ ;
    return( temp );
}


int main( int ac, char *av[] )
{
    static char first[] = {'F', 'I', 'R', 'S', 'T', 0x00 };
    static char *oops = "#define this that";

    printf("Starting #define test...\n");
    
    if ( 100 == 100 )
        printf("Correct!\n");
    else
        printf("Incorrect!\n");

    
    if ( ! strcmp( "#define this that", oops ) )
        printf("Correct!\n");
    else
        printf("Incorrect!\n");

    
    
    if ( ! strcmp("FIRST", first) )
        printf("Correct!\n");
    else
        printf("Incorrect!\n");

    
    printf("Starting include test...\n");

    if ( 1 )
        printf("Correct!\n");
    else
        printf("Incorrect!\n");

    printf("InCorrect!n"+2);
    printf("%c%c%c%c%c%c%c%c%c",'C','o','r',
           'r','e','c','t','!','n');
    printf("Correct!n");
    printf("Correct!n");
    printf(strdown("CORRECT!n"));

    printf("Starting misc. tests...\n");
    
    
    printf("Correct!\n");
    printf( "Correct!n" );

 printf("Final test coming up...n"); if ( 10 + 10 == 20 ) printf("Correct!n"); else printf("Incorrect!!!n");



    return( 0 );
}



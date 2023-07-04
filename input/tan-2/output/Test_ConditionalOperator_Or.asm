        Jump         $$main                    
        DLabel       $eat-location-zero        
        DataZ        8                         
        DLabel       $print-format-integer     
        DataC        37                        %% "%d"
        DataC        100                       
        DataC        0                         
        DLabel       $print-format-boolean     
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-float       
        DataC        37                        %% "%f"
        DataC        102                       
        DataC        0                         
        DLabel       $print-format-character   
        DataC        37                        %% "%c"
        DataC        99                        
        DataC        0                         
        DLabel       $print-format-string      
        DataC        37                        %% "%s"
        DataC        115                       
        DataC        0                         
        DLabel       $print-format-newline     
        DataC        10                        %% "\n"
        DataC        0                         
        DLabel       $print-format-space       
        DataC        32                        %% " "
        DataC        0                         
        DLabel       $print-format-tab         
        DataC        9                         %% "\t"
        DataC        0                         
        DLabel       $boolean-true-string      
        DataC        116                       %% "true"
        DataC        114                       
        DataC        117                       
        DataC        101                       
        DataC        0                         
        DLabel       $boolean-false-string     
        DataC        102                       %% "false"
        DataC        97                        
        DataC        108                       
        DataC        115                       
        DataC        101                       
        DataC        0                         
        DLabel       $errors-general-message   
        DataC        82                        %% "Runtime error: %s\n"
        DataC        117                       
        DataC        110                       
        DataC        116                       
        DataC        105                       
        DataC        109                       
        DataC        101                       
        DataC        32                        
        DataC        101                       
        DataC        114                       
        DataC        114                       
        DataC        111                       
        DataC        114                       
        DataC        58                        
        DataC        32                        
        DataC        37                        
        DataC        115                       
        DataC        10                        
        DataC        0                         
        Label        $$general-runtime-error   
        PushD        $errors-general-message   
        Printf                                 
        Halt                                   
        DLabel       $errors-int-divide-by-zero 
        DataC        105                       %% "integer divide by zero"
        DataC        110                       
        DataC        116                       
        DataC        101                       
        DataC        103                       
        DataC        101                       
        DataC        114                       
        DataC        32                        
        DataC        100                       
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        Label        $$i-divide-by-zero        
        PushD        $errors-int-divide-by-zero 
        Jump         $$general-runtime-error   
        DLabel       $errors-float-divide-by-zero 
        DataC        102                       %% "float divide by zero"
        DataC        108                       
        DataC        111                       
        DataC        97                        
        DataC        116                       
        DataC        32                        
        DataC        100                       
        DataC        105                       
        DataC        118                       
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        32                        
        DataC        98                        
        DataC        121                       
        DataC        32                        
        DataC        122                       
        DataC        101                       
        DataC        114                       
        DataC        111                       
        DataC        0                         
        Label        $$f-divide-by-zero        
        PushD        $errors-float-divide-by-zero 
        Jump         $$general-runtime-error   
        DLabel       $usable-memory-start      
        DLabel       $global-memory-block      
        DataZ        3                         
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        Label        -conditional-OR-1-arg1    
        PushI        1                         
        JumpTrue     -conditional-OR-1-true    
        Label        -conditional-OR-1-arg2    
        PushI        1                         
        JumpTrue     -conditional-OR-1-true    
        Label        -conditional-OR-1-false   
        PushI        0                         
        Jump         -conditional-OR-1-join    
        Label        -conditional-OR-1-true    
        PushI        1                         
        Label        -conditional-OR-1-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadC                                  
        JumpTrue     -print-boolean-2-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-2-join     
        Label        -print-boolean-2-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-2-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x1
        Label        -conditional-OR-3-arg1    
        PushI        1                         
        JumpTrue     -conditional-OR-3-true    
        Label        -conditional-OR-3-arg2    
        PushI        0                         
        JumpTrue     -conditional-OR-3-true    
        Label        -conditional-OR-3-false   
        PushI        0                         
        Jump         -conditional-OR-3-join    
        Label        -conditional-OR-3-true    
        PushI        1                         
        Label        -conditional-OR-3-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x1
        LoadC                                  
        JumpTrue     -print-boolean-4-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-4-join     
        Label        -print-boolean-4-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-4-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x1
        Label        -conditional-OR-5-arg1    
        PushI        0                         
        JumpTrue     -conditional-OR-5-true    
        Label        -conditional-OR-5-arg2    
        PushI        0                         
        JumpTrue     -conditional-OR-5-true    
        Label        -conditional-OR-5-false   
        PushI        0                         
        Jump         -conditional-OR-5-join    
        Label        -conditional-OR-5-true    
        PushI        1                         
        Label        -conditional-OR-5-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x1
        LoadC                                  
        JumpTrue     -print-boolean-6-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-6-join     
        Label        -print-boolean-6-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-6-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x1
        Label        -conditional-OR-7-arg1    
        PushI        0                         
        JumpTrue     -conditional-OR-7-true    
        Label        -conditional-OR-7-arg2    
        PushI        1                         
        JumpTrue     -conditional-OR-7-true    
        Label        -conditional-OR-7-false   
        PushI        0                         
        Jump         -conditional-OR-7-join    
        Label        -conditional-OR-7-true    
        PushI        1                         
        Label        -conditional-OR-7-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x1
        LoadC                                  
        JumpTrue     -print-boolean-8-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-8-join     
        Label        -print-boolean-8-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-8-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x2
        Label        -conditional-OR-12-arg1   
        PushI        12                        
        JumpTrue     -castIntToBool-9-true     
        PushI        0                         
        Jump         -castIntToBool-9-join     
        Label        -castIntToBool-9-true     
        PushI        1                         
        Label        -castIntToBool-9-join     
        JumpTrue     -conditional-OR-12-true   
        Label        -conditional-OR-12-arg2   
        PushI        12                        
        PushI        0                         
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-10-notZero        
        Divide                                 
        PushI        1                         
        Label        -compare-11-arg1          
        Label        -compare-11-arg2          
        Label        -compare-11-sub           
        Subtract                               
        JumpFalse    -compare-11-true          
        Jump         -compare-11-false         
        Label        -compare-11-true          
        PushI        1                         
        Jump         -compare-11-join          
        Label        -compare-11-false         
        PushI        0                         
        Jump         -compare-11-join          
        Label        -compare-11-join          
        JumpTrue     -conditional-OR-12-true   
        Label        -conditional-OR-12-false  
        PushI        0                         
        Jump         -conditional-OR-12-join   
        Label        -conditional-OR-12-true   
        PushI        1                         
        Label        -conditional-OR-12-join   
        StoreC                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x2
        LoadC                                  
        JumpTrue     -print-boolean-13-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-13-join    
        Label        -print-boolean-13-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-13-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x2
        Label        -conditional-OR-20-arg1   
        Label        -conditional-OR-18-arg1   
        Label        -conditional-OR-16-arg1   
        PushI        1                         
        PushI        2                         
        Add                                    
        PushI        5                         
        Label        -compare-14-arg1          
        Label        -compare-14-arg2          
        Label        -compare-14-sub           
        Subtract                               
        JumpFalse    -compare-14-true          
        Jump         -compare-14-false         
        Label        -compare-14-true          
        PushI        1                         
        Jump         -compare-14-join          
        Label        -compare-14-false         
        PushI        0                         
        Jump         -compare-14-join          
        Label        -compare-14-join          
        JumpTrue     -conditional-OR-16-true   
        Label        -conditional-OR-16-arg2   
        PushF        7.700000                  
        PushF        3.300000                  
        FSubtract    null                      
        PushF        0.000000                  
        Label        -compare-15-arg1          
        Label        -compare-15-arg2          
        Label        -compare-15-sub           
        FSubtract    null                      
        JumpFNeg     -compare-15-true          
        Jump         -compare-15-false         
        Label        -compare-15-true          
        PushI        1                         
        Jump         -compare-15-join          
        Label        -compare-15-false         
        PushI        0                         
        Jump         -compare-15-join          
        Label        -compare-15-join          
        JumpTrue     -conditional-OR-16-true   
        Label        -conditional-OR-16-false  
        PushI        0                         
        Jump         -conditional-OR-16-join   
        Label        -conditional-OR-16-true   
        PushI        1                         
        Label        -conditional-OR-16-join   
        JumpTrue     -conditional-OR-18-true   
        Label        -conditional-OR-18-arg2   
        PushI        97                        
        PushI        98                        
        Label        -compare-17-arg1          
        Label        -compare-17-arg2          
        Label        -compare-17-sub           
        Subtract                               
        JumpFalse    -compare-17-true          
        Jump         -compare-17-false         
        Label        -compare-17-true          
        PushI        1                         
        Jump         -compare-17-join          
        Label        -compare-17-false         
        PushI        0                         
        Jump         -compare-17-join          
        Label        -compare-17-join          
        JumpTrue     -conditional-OR-18-true   
        Label        -conditional-OR-18-false  
        PushI        0                         
        Jump         -conditional-OR-18-join   
        Label        -conditional-OR-18-true   
        PushI        1                         
        Label        -conditional-OR-18-join   
        JumpTrue     -conditional-OR-20-true   
        Label        -conditional-OR-20-arg2   
        PushI        97                        
        PushI        97                        
        Label        -compare-19-arg1          
        Label        -compare-19-arg2          
        Label        -compare-19-sub           
        Subtract                               
        JumpFalse    -compare-19-true          
        Jump         -compare-19-false         
        Label        -compare-19-true          
        PushI        1                         
        Jump         -compare-19-join          
        Label        -compare-19-false         
        PushI        0                         
        Jump         -compare-19-join          
        Label        -compare-19-join          
        JumpTrue     -conditional-OR-20-true   
        Label        -conditional-OR-20-false  
        PushI        0                         
        Jump         -conditional-OR-20-join   
        Label        -conditional-OR-20-true   
        PushI        1                         
        Label        -conditional-OR-20-join   
        StoreC                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x2
        LoadC                                  
        JumpTrue     -print-boolean-21-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-21-join    
        Label        -print-boolean-21-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-21-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x2
        Label        -conditional-OR-28-arg1   
        Label        -conditional-OR-26-arg1   
        Label        -conditional-OR-24-arg1   
        PushI        1                         
        PushI        2                         
        Add                                    
        PushI        5                         
        Label        -compare-22-arg1          
        Label        -compare-22-arg2          
        Label        -compare-22-sub           
        Subtract                               
        JumpFalse    -compare-22-true          
        Jump         -compare-22-false         
        Label        -compare-22-true          
        PushI        1                         
        Jump         -compare-22-join          
        Label        -compare-22-false         
        PushI        0                         
        Jump         -compare-22-join          
        Label        -compare-22-join          
        JumpTrue     -conditional-OR-24-true   
        Label        -conditional-OR-24-arg2   
        PushF        7.700000                  
        PushF        3.300000                  
        FSubtract    null                      
        PushF        0.000000                  
        Label        -compare-23-arg1          
        Label        -compare-23-arg2          
        Label        -compare-23-sub           
        FSubtract    null                      
        JumpFNeg     -compare-23-true          
        Jump         -compare-23-false         
        Label        -compare-23-true          
        PushI        1                         
        Jump         -compare-23-join          
        Label        -compare-23-false         
        PushI        0                         
        Jump         -compare-23-join          
        Label        -compare-23-join          
        JumpTrue     -conditional-OR-24-true   
        Label        -conditional-OR-24-false  
        PushI        0                         
        Jump         -conditional-OR-24-join   
        Label        -conditional-OR-24-true   
        PushI        1                         
        Label        -conditional-OR-24-join   
        JumpTrue     -conditional-OR-26-true   
        Label        -conditional-OR-26-arg2   
        PushI        97                        
        PushI        98                        
        Label        -compare-25-arg1          
        Label        -compare-25-arg2          
        Label        -compare-25-sub           
        Subtract                               
        JumpFalse    -compare-25-true          
        Jump         -compare-25-false         
        Label        -compare-25-true          
        PushI        1                         
        Jump         -compare-25-join          
        Label        -compare-25-false         
        PushI        0                         
        Jump         -compare-25-join          
        Label        -compare-25-join          
        JumpTrue     -conditional-OR-26-true   
        Label        -conditional-OR-26-false  
        PushI        0                         
        Jump         -conditional-OR-26-join   
        Label        -conditional-OR-26-true   
        PushI        1                         
        Label        -conditional-OR-26-join   
        JumpTrue     -conditional-OR-28-true   
        Label        -conditional-OR-28-arg2   
        PushI        97                        
        PushI        99                        
        Label        -compare-27-arg1          
        Label        -compare-27-arg2          
        Label        -compare-27-sub           
        Subtract                               
        JumpFalse    -compare-27-true          
        Jump         -compare-27-false         
        Label        -compare-27-true          
        PushI        1                         
        Jump         -compare-27-join          
        Label        -compare-27-false         
        PushI        0                         
        Jump         -compare-27-join          
        Label        -compare-27-join          
        JumpTrue     -conditional-OR-28-true   
        Label        -conditional-OR-28-false  
        PushI        0                         
        Jump         -conditional-OR-28-join   
        Label        -conditional-OR-28-true   
        PushI        1                         
        Label        -conditional-OR-28-join   
        StoreC                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x2
        LoadC                                  
        JumpTrue     -print-boolean-29-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-29-join    
        Label        -print-boolean-29-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-29-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   

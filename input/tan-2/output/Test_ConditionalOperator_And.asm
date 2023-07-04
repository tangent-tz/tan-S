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
        DataZ        34                        
        Label        $$main                    
        Label        -conditional-AND-2-arg1   
        Label        -conditional-AND-1-arg1   
        PushI        1                         
        JumpFalse    -conditional-AND-1-false  
        Label        -conditional-AND-1-arg2   
        PushI        1                         
        JumpTrue     -conditional-AND-1-true   
        Label        -conditional-AND-1-false  
        PushI        0                         
        Jump         -conditional-AND-1-join   
        Label        -conditional-AND-1-true   
        PushI        1                         
        Label        -conditional-AND-1-join   
        JumpFalse    -conditional-AND-2-false  
        Label        -conditional-AND-2-arg2   
        PushI        1                         
        JumpTrue     -conditional-AND-2-true   
        Label        -conditional-AND-2-false  
        PushI        0                         
        Jump         -conditional-AND-2-join   
        Label        -conditional-AND-2-true   
        PushI        1                         
        Label        -conditional-AND-2-join   
        JumpTrue     -print-boolean-3-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-3-join     
        Label        -print-boolean-3-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-3-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -conditional-AND-5-arg1   
        Label        -conditional-AND-4-arg1   
        PushI        1                         
        JumpFalse    -conditional-AND-4-false  
        Label        -conditional-AND-4-arg2   
        PushI        0                         
        JumpTrue     -conditional-AND-4-true   
        Label        -conditional-AND-4-false  
        PushI        0                         
        Jump         -conditional-AND-4-join   
        Label        -conditional-AND-4-true   
        PushI        1                         
        Label        -conditional-AND-4-join   
        JumpFalse    -conditional-AND-5-false  
        Label        -conditional-AND-5-arg2   
        PushI        1                         
        JumpTrue     -conditional-AND-5-true   
        Label        -conditional-AND-5-false  
        PushI        0                         
        Jump         -conditional-AND-5-join   
        Label        -conditional-AND-5-true   
        PushI        1                         
        Label        -conditional-AND-5-join   
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
        Label        -conditional-AND-8-arg1   
        Label        -conditional-AND-7-arg1   
        PushI        0                         
        JumpFalse    -conditional-AND-7-false  
        Label        -conditional-AND-7-arg2   
        PushI        1                         
        JumpTrue     -conditional-AND-7-true   
        Label        -conditional-AND-7-false  
        PushI        0                         
        Jump         -conditional-AND-7-join   
        Label        -conditional-AND-7-true   
        PushI        1                         
        Label        -conditional-AND-7-join   
        JumpFalse    -conditional-AND-8-false  
        Label        -conditional-AND-8-arg2   
        PushI        1                         
        JumpTrue     -conditional-AND-8-true   
        Label        -conditional-AND-8-false  
        PushI        0                         
        Jump         -conditional-AND-8-join   
        Label        -conditional-AND-8-true   
        PushI        1                         
        Label        -conditional-AND-8-join   
        JumpTrue     -print-boolean-9-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-9-join     
        Label        -print-boolean-9-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-9-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -conditional-AND-11-arg1  
        Label        -conditional-AND-10-arg1  
        PushI        0                         
        JumpFalse    -conditional-AND-10-false 
        Label        -conditional-AND-10-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-10-true  
        Label        -conditional-AND-10-false 
        PushI        0                         
        Jump         -conditional-AND-10-join  
        Label        -conditional-AND-10-true  
        PushI        1                         
        Label        -conditional-AND-10-join  
        JumpFalse    -conditional-AND-11-false 
        Label        -conditional-AND-11-arg2  
        PushI        1                         
        JumpTrue     -conditional-AND-11-true  
        Label        -conditional-AND-11-false 
        PushI        0                         
        Jump         -conditional-AND-11-join  
        Label        -conditional-AND-11-true  
        PushI        1                         
        Label        -conditional-AND-11-join  
        JumpTrue     -print-boolean-12-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-12-join    
        Label        -print-boolean-12-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-12-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -conditional-AND-14-arg1  
        Label        -conditional-AND-13-arg1  
        PushI        1                         
        JumpFalse    -conditional-AND-13-false 
        Label        -conditional-AND-13-arg2  
        PushI        1                         
        JumpTrue     -conditional-AND-13-true  
        Label        -conditional-AND-13-false 
        PushI        0                         
        Jump         -conditional-AND-13-join  
        Label        -conditional-AND-13-true  
        PushI        1                         
        Label        -conditional-AND-13-join  
        JumpFalse    -conditional-AND-14-false 
        Label        -conditional-AND-14-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-14-true  
        Label        -conditional-AND-14-false 
        PushI        0                         
        Jump         -conditional-AND-14-join  
        Label        -conditional-AND-14-true  
        PushI        1                         
        Label        -conditional-AND-14-join  
        JumpTrue     -print-boolean-15-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-15-join    
        Label        -print-boolean-15-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-15-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -conditional-AND-17-arg1  
        Label        -conditional-AND-16-arg1  
        PushI        1                         
        JumpFalse    -conditional-AND-16-false 
        Label        -conditional-AND-16-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-16-true  
        Label        -conditional-AND-16-false 
        PushI        0                         
        Jump         -conditional-AND-16-join  
        Label        -conditional-AND-16-true  
        PushI        1                         
        Label        -conditional-AND-16-join  
        JumpFalse    -conditional-AND-17-false 
        Label        -conditional-AND-17-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-17-true  
        Label        -conditional-AND-17-false 
        PushI        0                         
        Jump         -conditional-AND-17-join  
        Label        -conditional-AND-17-true  
        PushI        1                         
        Label        -conditional-AND-17-join  
        JumpTrue     -print-boolean-18-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-18-join    
        Label        -print-boolean-18-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-18-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Label        -conditional-AND-20-arg1  
        Label        -conditional-AND-19-arg1  
        PushI        0                         
        JumpFalse    -conditional-AND-19-false 
        Label        -conditional-AND-19-arg2  
        PushI        1                         
        JumpTrue     -conditional-AND-19-true  
        Label        -conditional-AND-19-false 
        PushI        0                         
        Jump         -conditional-AND-19-join  
        Label        -conditional-AND-19-true  
        PushI        1                         
        Label        -conditional-AND-19-join  
        JumpFalse    -conditional-AND-20-false 
        Label        -conditional-AND-20-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-20-true  
        Label        -conditional-AND-20-false 
        PushI        0                         
        Jump         -conditional-AND-20-join  
        Label        -conditional-AND-20-true  
        PushI        1                         
        Label        -conditional-AND-20-join  
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
        Label        -conditional-AND-23-arg1  
        Label        -conditional-AND-22-arg1  
        PushI        0                         
        JumpFalse    -conditional-AND-22-false 
        Label        -conditional-AND-22-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-22-true  
        Label        -conditional-AND-22-false 
        PushI        0                         
        Jump         -conditional-AND-22-join  
        Label        -conditional-AND-22-true  
        PushI        1                         
        Label        -conditional-AND-22-join  
        JumpFalse    -conditional-AND-23-false 
        Label        -conditional-AND-23-arg2  
        PushI        0                         
        JumpTrue     -conditional-AND-23-true  
        Label        -conditional-AND-23-false 
        PushI        0                         
        Jump         -conditional-AND-23-join  
        Label        -conditional-AND-23-true  
        PushI        1                         
        Label        -conditional-AND-23-join  
        JumpTrue     -print-boolean-24-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-24-join    
        Label        -print-boolean-24-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-24-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% b
        PushI        20                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% c
        PushI        30                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% e
        PushI        40                        
        StoreI                                 
        Label        -conditional-AND-28-arg1  
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% b
        LoadI                                  
        Add                                    
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% c
        LoadI                                  
        Multiply                               
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% e
        LoadI                                  
        Label        -compare-25-arg1          
        Label        -compare-25-arg2          
        Label        -compare-25-sub           
        Subtract                               
        JumpPos      -compare-25-true          
        Jump         -compare-25-false         
        Label        -compare-25-true          
        PushI        1                         
        Jump         -compare-25-join          
        Label        -compare-25-false         
        PushI        0                         
        Jump         -compare-25-join          
        Label        -compare-25-join          
        JumpFalse    -conditional-AND-28-false 
        Label        -conditional-AND-28-arg2  
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% e
        LoadI                                  
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% a
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-26-notZero        
        Divide                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% b
        LoadI                                  
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
        JumpTrue     -conditional-AND-28-true  
        Label        -conditional-AND-28-false 
        PushI        0                         
        Jump         -conditional-AND-28-join  
        Label        -conditional-AND-28-true  
        PushI        1                         
        Label        -conditional-AND-28-join  
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
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% aa
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% bb
        PushI        20                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% cc
        PushI        30                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% ee
        PushI        200                       
        StoreI                                 
        Label        -conditional-AND-33-arg1  
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% aa
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% bb
        LoadI                                  
        Add                                    
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% cc
        LoadI                                  
        Multiply                               
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% ee
        LoadI                                  
        Label        -compare-30-arg1          
        Label        -compare-30-arg2          
        Label        -compare-30-sub           
        Subtract                               
        JumpPos      -compare-30-true          
        Jump         -compare-30-false         
        Label        -compare-30-true          
        PushI        1                         
        Jump         -compare-30-join          
        Label        -compare-30-false         
        PushI        0                         
        Jump         -compare-30-join          
        Label        -compare-30-join          
        JumpFalse    -conditional-AND-33-false 
        Label        -conditional-AND-33-arg2  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% ee
        LoadI                                  
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% aa
        LoadI                                  
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-31-notZero        
        Divide                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% bb
        LoadI                                  
        Label        -compare-32-arg1          
        Label        -compare-32-arg2          
        Label        -compare-32-sub           
        Subtract                               
        JumpFalse    -compare-32-true          
        Jump         -compare-32-false         
        Label        -compare-32-true          
        PushI        1                         
        Jump         -compare-32-join          
        Label        -compare-32-false         
        PushI        0                         
        Jump         -compare-32-join          
        Label        -compare-32-join          
        JumpTrue     -conditional-AND-33-true  
        Label        -conditional-AND-33-false 
        PushI        0                         
        Jump         -conditional-AND-33-join  
        Label        -conditional-AND-33-true  
        PushI        1                         
        Label        -conditional-AND-33-join  
        JumpTrue     -print-boolean-34-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-34-join    
        Label        -print-boolean-34-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-34-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% d
        Label        -conditional-AND-37-arg1  
        PushI        0                         
        JumpFalse    -conditional-AND-37-false 
        Label        -conditional-AND-37-arg2  
        PushI        17                        
        PushI        0                         
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-35-notZero        
        Divide                                 
        PushI        1                         
        Label        -compare-36-arg1          
        Label        -compare-36-arg2          
        Label        -compare-36-sub           
        Subtract                               
        JumpPos      -compare-36-true          
        Jump         -compare-36-false         
        Label        -compare-36-true          
        PushI        1                         
        Jump         -compare-36-join          
        Label        -compare-36-false         
        PushI        0                         
        Jump         -compare-36-join          
        Label        -compare-36-join          
        JumpTrue     -conditional-AND-37-true  
        Label        -conditional-AND-37-false 
        PushI        0                         
        Jump         -conditional-AND-37-join  
        Label        -conditional-AND-37-true  
        PushI        1                         
        Label        -conditional-AND-37-join  
        StoreC                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% d
        LoadC                                  
        JumpTrue     -print-boolean-38-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-38-join    
        Label        -print-boolean-38-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-38-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        33                        
        Add                                    %% v
        Label        -conditional-AND-41-arg1  
        PushI        1                         
        JumpFalse    -conditional-AND-41-false 
        Label        -conditional-AND-41-arg2  
        PushI        17                        
        PushI        0                         
        Duplicate                              
        JumpFalse    $$i-divide-by-zero        
        Label        -divide-39-notZero        
        Divide                                 
        PushI        1                         
        Label        -compare-40-arg1          
        Label        -compare-40-arg2          
        Label        -compare-40-sub           
        Subtract                               
        JumpPos      -compare-40-true          
        Jump         -compare-40-false         
        Label        -compare-40-true          
        PushI        1                         
        Jump         -compare-40-join          
        Label        -compare-40-false         
        PushI        0                         
        Jump         -compare-40-join          
        Label        -compare-40-join          
        JumpTrue     -conditional-AND-41-true  
        Label        -conditional-AND-41-false 
        PushI        0                         
        Jump         -conditional-AND-41-join  
        Label        -conditional-AND-41-true  
        PushI        1                         
        Label        -conditional-AND-41-join  
        StoreC                                 
        PushD        $global-memory-block      
        PushI        33                        
        Add                                    %% v
        LoadC                                  
        JumpTrue     -print-boolean-42-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-42-join    
        Label        -print-boolean-42-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-42-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   

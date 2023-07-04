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
        DataZ        76                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        PushI        5                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Subtract                               
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% a
        PushI        7                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% b
        PushI        3                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        8                         
        Add                                    %% a
        LoadI                                  
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% b
        LoadI                                  
        Subtract                               
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        PushI        15                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% d
        PushI        2                         
        Negate                                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% d
        LoadI                                  
        Subtract                               
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% e
        PushI        10                        
        StoreI                                 
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% f
        PushI        0                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% e
        LoadI                                  
        PushD        $global-memory-block      
        PushI        28                        
        Add                                    %% f
        LoadI                                  
        Subtract                               
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% g
        PushI        999999999                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% h
        PushI        888888888                 
        StoreI                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% g
        LoadI                                  
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% h
        LoadI                                  
        Subtract                               
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% exprone
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Add                                    
        PushI        3                         
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% exprone
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% exprtwo
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% c
        LoadI                                  
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% d
        LoadI                                  
        Subtract                               
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Subtract                               
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        44                        
        Add                                    %% exprtwo
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% zeroresult
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% zeroresult
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% zerooperand
        PushI        0                         
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% zerooperand
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% bothnegative
        PushI        10                        
        Negate                                 
        PushI        3                         
        Negate                                 
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        56                        
        Add                                    %% bothnegative
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        60                        
        Add                                    %% negativeresult
        PushI        5                         
        PushI        10                        
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        60                        
        Add                                    %% negativeresult
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% largenegative
        PushI        999999                    
        Negate                                 
        PushI        888888                    
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        64                        
        Add                                    %% largenegative
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        68                        
        Add                                    %% result
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Multiply                               
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Add                                    
        Subtract                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        68                        
        Add                                    %% result
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% multiops
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Subtract                               
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Add                                    
        Subtract                               
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x
        LoadI                                  
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y
        LoadI                                  
        Multiply                               
        Add                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        72                        
        Add                                    %% multiops
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        Halt                                   

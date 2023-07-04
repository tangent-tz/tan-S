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
        DataZ        60                        
        Label        $$main                    
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x1
        PushI        1                         
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% x1
        LoadC                                  
        JumpTrue     -print-boolean-1-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-1-join     
        Label        -print-boolean-1-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-1-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x2
        PushI        0                         
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        1                         
        Add                                    %% x2
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
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x3
        PushI        97                        
        JumpTrue     -castCharToBool-3-true    
        PushI        0                         
        Jump         -castCharToBool-3-join    
        Label        -castCharToBool-3-true    
        PushI        1                         
        Label        -castCharToBool-3-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        2                         
        Add                                    %% x3
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
        PushI        3                         
        Add                                    %% y1
        PushI        91                        
        JumpTrue     -castCharToBool-5-true    
        PushI        0                         
        Jump         -castCharToBool-5-join    
        Label        -castCharToBool-5-true    
        PushI        1                         
        Label        -castCharToBool-5-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        3                         
        Add                                    %% y1
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
        PushI        4                         
        Add                                    %% y2
        PushI        0                         
        JumpTrue     -castCharToBool-7-true    
        PushI        0                         
        Jump         -castCharToBool-7-join    
        Label        -castCharToBool-7-true    
        PushI        1                         
        Label        -castCharToBool-7-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% y2
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
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        5                         
        Add                                    %% x4
        PushI        84                        
        Nop                                    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        5                         
        Add                                    %% x4
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        6                         
        Add                                    %% x5
        PushI        33                        
        Nop                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        6                         
        Add                                    %% x5
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        10                        
        Add                                    %% x6
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        5                         
        DataC        104                       %% "hello"
        DataC        101                       
        DataC        108                       
        DataC        108                       
        DataC        111                       
        DataC        0                         
        PushD        _string_1_                
        Nop                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        10                        
        Add                                    %% x6
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        14                        
        Add                                    %% x7
        PushI        1010                      
        JumpTrue     -castIntToBool-9-true     
        PushI        0                         
        Jump         -castIntToBool-9-join     
        Label        -castIntToBool-9-true     
        PushI        1                         
        Label        -castIntToBool-9-join     
        StoreC                                 
        PushD        $global-memory-block      
        PushI        14                        
        Add                                    %% x7
        LoadC                                  
        JumpTrue     -print-boolean-10-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-10-join    
        Label        -print-boolean-10-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-10-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        15                        
        Add                                    %% x8
        PushI        0                         
        JumpTrue     -castIntToBool-11-true    
        PushI        0                         
        Jump         -castIntToBool-11-join    
        Label        -castIntToBool-11-true    
        PushI        1                         
        Label        -castIntToBool-11-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        15                        
        Add                                    %% x8
        LoadC                                  
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
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y3
        PushI        1                         
        Negate                                 
        JumpTrue     -castIntToBool-13-true    
        PushI        0                         
        Jump         -castIntToBool-13-join    
        Label        -castIntToBool-13-true    
        PushI        1                         
        Label        -castIntToBool-13-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        16                        
        Add                                    %% y3
        LoadC                                  
        JumpTrue     -print-boolean-14-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-14-join    
        Label        -print-boolean-14-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-14-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% y4
        PushI        1                         
        JumpTrue     -castIntToBool-15-true    
        PushI        0                         
        Jump         -castIntToBool-15-join    
        Label        -castIntToBool-15-true    
        PushI        1                         
        Label        -castIntToBool-15-join    
        StoreC                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% y4
        LoadC                                  
        JumpTrue     -print-boolean-16-true    
        PushD        $boolean-false-string     
        Jump         -print-boolean-16-join    
        Label        -print-boolean-16-true    
        PushD        $boolean-true-string      
        Label        -print-boolean-16-join    
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% x9
        PushI        126                       
        PushI        127                       
        BTAnd                                  
        StoreC                                 
        PushD        $global-memory-block      
        PushI        18                        
        Add                                    %% x9
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        19                        
        Add                                    %% x10
        PushI        161                       
        PushI        127                       
        BTAnd                                  
        StoreC                                 
        PushD        $global-memory-block      
        PushI        19                        
        Add                                    %% x10
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% x11
        PushI        1123                      
        Nop                                    
        StoreI                                 
        PushD        $global-memory-block      
        PushI        20                        
        Add                                    %% x11
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% x12
        PushI        56                        
        ConvertF                               
        StoreF                                 
        PushD        $global-memory-block      
        PushI        24                        
        Add                                    %% x12
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% x13
        PushF        34.680000                 
        ConvertI                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        32                        
        Add                                    %% x13
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% y5
        PushF        12.435000                 
        FNegate      null                      
        ConvertI                               
        StoreI                                 
        PushD        $global-memory-block      
        PushI        36                        
        Add                                    %% y5
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% x14
        PushF        12.450000                 
        Nop                                    
        StoreF                                 
        PushD        $global-memory-block      
        PushI        40                        
        Add                                    %% x14
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% a
        PushI        5                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% b
        PushF        6.000000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% b
        PushD        $global-memory-block      
        PushI        48                        
        Add                                    %% a
        LoadI                                  
        ConvertF                               
        StoreF                                 
        PushD        $global-memory-block      
        PushI        52                        
        Add                                    %% b
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        Halt                                   

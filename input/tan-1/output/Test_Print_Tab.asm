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
        DataZ        18                        
        Label        $$main                    
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_14_               
        DataI        3                         
        DataI        9                         
        DataI        7                         
        DataC        124                       %% "|ident1"
        DataC        105                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        49                        
        DataC        0                         
        PushD        _string_14_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_13_               
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent2"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        50                        
        DataC        0                         
        PushD        _string_13_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_12_               
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent3"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        51                        
        DataC        0                         
        PushD        _string_12_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_11_               
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent4"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        52                        
        DataC        0                         
        PushD        _string_11_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_10_               
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent5"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        53                        
        DataC        0                         
        PushD        _string_10_               
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_9_                
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent6"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        54                        
        DataC        0                         
        PushD        _string_9_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_8_                
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent7"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        55                        
        DataC        0                         
        PushD        _string_8_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_7_                
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent8"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        56                        
        DataC        0                         
        PushD        _string_7_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_6_                
        DataI        3                         
        DataI        9                         
        DataI        8                         
        DataC        124                       %% "|indent9"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        57                        
        DataC        0                         
        PushD        _string_6_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_5_                
        DataI        3                         
        DataI        9                         
        DataI        9                         
        DataC        124                       %% "|indent10"
        DataC        105                       
        DataC        110                       
        DataC        100                       
        DataC        101                       
        DataC        110                       
        DataC        116                       
        DataC        49                        
        DataC        48                        
        DataC        0                         
        PushD        _string_5_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        DLabel       _string_4_                
        DataI        3                         
        DataI        9                         
        DataI        2                         
        DataC        104                       %% "hi"
        DataC        105                       
        DataC        0                         
        PushD        _string_4_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        DLabel       _string_3_                
        DataI        3                         
        DataI        9                         
        DataI        6                         
        DataC        116                       %% "tanzil"
        DataC        97                        
        DataC        110                       
        DataC        122                       
        DataC        105                       
        DataC        108                       
        DataC        0                         
        PushD        _string_3_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushI        119                       
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushI        123                       
        PushI        10                        
        Add                                    
        PushD        $print-format-integer     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushF        1.456000                  
        FNegate      null                      
        PushF        10.000000                 
        FMultiply    null                      
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushI        1                         
        JumpTrue     -print-boolean-1-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-1-join     
        Label        -print-boolean-1-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-1-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushI        0                         
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
        DLabel       _string_2_                
        DataI        3                         
        DataI        9                         
        DataI        18                        
        DataC        116                       %% "this is a new line"
        DataC        104                       
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        97                        
        DataC        32                        
        DataC        110                       
        DataC        101                       
        DataC        119                       
        DataC        32                        
        DataC        108                       
        DataC        105                       
        DataC        110                       
        DataC        101                       
        DataC        0                         
        PushD        _string_2_                
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        PushI        1                         
        StoreI                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        PushF        2.200000                  
        StoreF                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% k
        PushI        114                       
        StoreC                                 
        PushD        $global-memory-block      
        PushI        13                        
        Add                                    %% l
        DLabel       _string_1_                
        DataI        3                         
        DataI        9                         
        DataI        9                         
        DataC        116                       %% "this is l"
        DataC        104                       
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        105                       
        DataC        115                       
        DataC        32                        
        DataC        108                       
        DataC        0                         
        PushD        _string_1_                
        StoreI                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% m
        PushI        1                         
        StoreC                                 
        PushD        $global-memory-block      
        PushI        0                         
        Add                                    %% i
        LoadI                                  
        PushD        $print-format-integer     
        Printf                                 
        PushD        $global-memory-block      
        PushI        4                         
        Add                                    %% j
        LoadF                                  
        PushD        $print-format-float       
        Printf                                 
        PushD        $print-format-newline     
        Printf                                 
        PushD        $global-memory-block      
        PushI        12                        
        Add                                    %% k
        LoadC                                  
        PushD        $print-format-character   
        Printf                                 
        PushD        $print-format-space       
        Printf                                 
        PushD        $global-memory-block      
        PushI        13                        
        Add                                    %% l
        LoadI                                  
        PushI        12                        
        Add                                    
        PushD        $print-format-string      
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $global-memory-block      
        PushI        17                        
        Add                                    %% m
        LoadC                                  
        JumpTrue     -print-boolean-3-true     
        PushD        $boolean-false-string     
        Jump         -print-boolean-3-join     
        Label        -print-boolean-3-true     
        PushD        $boolean-true-string      
        Label        -print-boolean-3-join     
        PushD        $print-format-boolean     
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushD        $print-format-tab         
        Printf                                 
        PushI        0                         
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
        Halt                                   

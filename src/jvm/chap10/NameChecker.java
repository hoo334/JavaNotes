package jvm.chap10;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;
import java.util.EnumSet;

public class NameChecker {
    private final Messager messager;

    NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processingEnvironment){
        this.messager = processingEnvironment.getMessager();
    }

    public void checkNames(Element element){
        nameCheckScanner.scan(element);
    }

    /**
     * 名称检查器类，将会以 Visitor 模式访问抽象语法树中的元素
     * */
    public class NameCheckScanner extends ElementScanner8<Void, Void> {

        /**
         * 此方法用于检查 Java 类
         * */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            super.visitType(e, aVoid);
            return null;
        }

        /**
         * 检查方法名是否合法
         * */
        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if(e.getKind() == ElementKind.METHOD){
                Name name = e.getSimpleName();
                if(name.contentEquals(e.getEnclosingElement().getSimpleName())){
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法 '" + name + "'不应当与类名重复，避免构造函数产生混淆", e);
                }
                checkCamelCase(e, false);
            }
            super.visitExecutable(e, aVoid);
            return null;
        }

        /**
         * 检查变量名是否合法
         * */
        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            // 如果这个 Variable 是枚举或常量，则按大写命名检查，否则按驼峰命名检查
            if(e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)){
                checkAllCaps(e);
            }else{
                checkCamelCase(e, false);
            }
            return null;
        }

        /**
         * 判断一个变量是否是常量
         * */
        private boolean heuristicallyConstant(VariableElement e){
            //接口里的变量是常量，同时用 public static final 修饰的是常量
            if(e.getEnclosingElement().getKind() == ElementKind.INTERFACE){
                return true;
            }else if(e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))){
                return true;
            }else{
                return false;
            }
        }

        /**
         * 检查传入的 Element 是否符合驼峰命名，如果不符合，则输出警告信息
         * @param e 被检查的元素
         * @param initialCaps 首字母是否大写
         * */
        private void checkCamelCase(Element e, boolean initialCaps){
            String name = e.getSimpleName().toString();
            //前一个字母是否为大写
            boolean previousUpper = false;
            //检查结果
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            //判断首字母是否合法
            if(Character.isUpperCase(firstCodePoint)){
                previousUpper = true;
                if(!initialCaps){
                    //首字母为大写，但需要的是首字母小写
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称'" + name + "'应当以小写字母开头", e);
                    return;
                }
            }else if(Character.isLowerCase(firstCodePoint)){
                if(initialCaps){
                    //首字母为大写，但需要的是首字母小写
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称'" + name + "'应以大写字母开头", e);
                    return;
                }
            }else{
                //首字母为其他字符，直接判定为错误
                conventional = false;
            }

            if(conventional){
                int cp = firstCodePoint;
                for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(Character.isUpperCase(cp)){
                        if(previousUpper){
                            //当前字母为大写，前一个字母也为大写，判为错误，退出判断
                            conventional = false;
                            break;
                        }
                        previousUpper = true;
                    }else{
                        previousUpper = false;
                    }
                }
            }

            if(!conventional){
                messager.printMessage(Diagnostic.Kind.WARNING, "名称'" + name + "'应符合驼峰命名法", e);
            }
        }

        /**
         * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母
         * @param e 被检查的元素
         * */
        private void checkAllCaps(Element e){
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if(!Character.isUpperCase(firstCodePoint)){
                //首字母不是大写字母，判为错误
                conventional = false;
            }else{
                //前一个字符是否为下划线
                boolean previousUnderScore = false;

                int cp = firstCodePoint;
                for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
                    cp = name.codePointAt(i);
                    if(cp == (int) '_'){
                        if(previousUnderScore){
                            //连续出现两个下划线，判为错误
                            conventional = false;
                            break;
                        }
                        previousUnderScore = true;
                    }else{
                        previousUnderScore = false;
                        //除了大写字母、数字和下划线外出现了别的字符
                        if(!Character.isUpperCase(cp) && !Character.isDigit(cp)){
                            conventional = false;
                            break;
                        }
                    }
                }
            }

            if(!conventional){
                messager.printMessage(Diagnostic.Kind.WARNING, "常量 '" + name + "'应全部是大写字母和下划线且必须以大写字母开头", e);
            }
        }
    }

}

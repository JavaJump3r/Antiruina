package com.github.javajump3r.mixin;

import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.LockButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AddServerScreen.class)
public class IpAdressMixin extends Screen{
    protected IpAdressMixin(Text title) {
        super(title);
    }
    @Shadow private TextFieldWidget addressField;

    @Inject(method = "init",at = @At(value = "INVOKE",ordinal = 1,target = "net/minecraft/client/gui/screen/AddServerScreen.addSelectableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;"))
    private void antiRuina$inject(CallbackInfo ci)
    {
        this.addressField.setRenderTextProvider((inputString, inputInt) -> Text.literal(StringUtils.repeat('*',inputString.length())).asOrderedText());

        var showIpButton = new LockButtonWidget(addressField.x+addressField.getWidth()+5, addressField.y,(button)->{
            var lockButton = ((LockButtonWidget)button);
            lockButton.setLocked(!lockButton.isLocked());
            if(lockButton.isLocked()){
                this.addressField.setRenderTextProvider((inputString, inputInt) -> {
                    String stars = StringUtils.repeat('*',inputString.length());
                    return Text.literal(stars).asOrderedText();
                });
            }
            else {
                this.addressField.setRenderTextProvider((inputString, inputInt) -> Text.literal(inputString).asOrderedText());
            }

        });
        showIpButton.setLocked(true);
        addDrawableChild(showIpButton);

    }
}

// textFieldWidget.setRenderTextProvider((inputString, inputInt) -> Text.literal(StringUtils.repeat('*',inputString.length())).asOrderedText());
//

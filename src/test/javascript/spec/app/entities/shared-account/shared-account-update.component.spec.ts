/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoolPassTestModule } from '../../../test.module';
import { SharedAccountUpdateComponent } from 'app/entities/shared-account/shared-account-update.component';
import { SharedAccountService } from 'app/entities/shared-account/shared-account.service';
import { SharedAccount } from 'app/shared/model/shared-account.model';

describe('Component Tests', () => {
    describe('SharedAccount Management Update Component', () => {
        let comp: SharedAccountUpdateComponent;
        let fixture: ComponentFixture<SharedAccountUpdateComponent>;
        let service: SharedAccountService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoolPassTestModule],
                declarations: [SharedAccountUpdateComponent]
            })
                .overrideTemplate(SharedAccountUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SharedAccountUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SharedAccountService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SharedAccount(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sharedAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SharedAccount();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sharedAccount = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
